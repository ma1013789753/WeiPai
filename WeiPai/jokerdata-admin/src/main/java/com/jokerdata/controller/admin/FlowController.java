package com.jokerdata.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.config.AlipayConfig;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.admin.*;
import com.jokerdata.service.app.PdCashService;
import com.jokerdata.service.app.PdLogService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.vo.AliPayVo;
import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  流水 前端控制器
 * </p>
 *
 * @author aozhang
 * @since 2019-06-02
 */
@RestController
@RequestMapping("/flow")
@Transactional(rollbackFor = ApiException.class)
public class FlowController {

    @Autowired
    private CustomPdCashService customPdCashService;

    @Autowired
    private CustomPdLogService customPdLogService;

    @Autowired
    private CustomCoinLogService customCoinLogService;

    @Autowired
    private CustomCoinService customCoinService;

    @Autowired
    private PdCashService pdCashService;

    @Autowired
    private PdLogService pdLogService;

    @Autowired
    private UserService userService;

    @Login
    @PostMapping(value = "/getCashPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取提现列表",notes = "")
    public Result getCashPage(@RequestBody MyPage page,@RequestParam int type){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("pdc_add_time");
        //提现申请
        if(type == 0){
            queryWrapper.eq("pdc_payment_state",0);

        }else if(type == 1){
            queryWrapper.eq("pdc_payment_state",1);
        }else {
            queryWrapper.eq("pdc_payment_state",2);
        }
        queryWrapper.like("pdc_sn",page.getSearch1());
        queryWrapper.like("pdc_member_name",page.getSearch2());
            IPage<PdCash> data = customPdCashService.page(page,queryWrapper);
            return Result.success(data);
    }


    @Login
    @PostMapping(value = "/getCashFlow",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取资金列表",notes = "")
    public Result getCashFlow(@RequestBody MyPage page){
        IPage<PdLog> data = customPdLogService.page(page, new QueryWrapper<PdLog>()
                .orderByDesc("lg_add_time")
                .like("lg_member_name",page.getSearch1()));
        return Result.success(data);
    }

    @Login
    @PostMapping(value = "/caseApprove",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审核通过",notes = "")
    public Result caseApprove(@RequestBody String id){

        PdCash pdCash = pdCashService.getById(id);
        PdLog pdLog = pdLogService.getOne(new QueryWrapper<PdLog>().eq("lg_from_data",pdCash.getPdcId()));
        if(pdLog == null){
            throw new ApiException("记录不存在");
        }
        pdCash.setPdcPaymentTime((int) (new Date().getTime()/1000));
        pdCash.setPdcPaymentState(1);
        pdCash.setPdcPaymentAdmin("admin");
        if(!pdCashService.updateById(pdCash)){
            throw new ApiException("记录不存在");
        }

        pdLog.setLgType("cash_pay");
        if(!pdLogService.updateById(pdLog)){
            throw new ApiException("更新失败");
        }

        User user = userService.getById(pdCash.getPdcMemberId());
        user.setFreezePredeposit(user.getFreezePredeposit().subtract(pdLog.getLgAvAmount()));
        if(!userService.updateById(user)){
            throw new ApiException("更新失败");
        }

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        Map<String,String> resultMap=new HashMap<String,String>();
        AliPayVo vo = new AliPayVo();
        vo.setOut_biz_no(pdCash.getPdcSn());
        vo.setPayee_type("ALIPAY_LOGONID");
        vo.setAmount(String.valueOf(pdCash.getPdcAmount().doubleValue()));
        vo.setPayee_account(pdCash.getPdcBankNo());
        vo.setPayer_show_name(pdCash.getPdcBankUser());
        vo.setPayee_real_name(pdCash.getPdcBankUser());
        vo.setRemark("支付宝转账");
        String json = new Gson().toJson(vo);
        // 设置请求参数
        AlipayFundTransToaccountTransferRequest alipayRequest = new AlipayFundTransToaccountTransferRequest();
        alipayRequest.setBizContent(json);
        AlipayFundTransToaccountTransferResponse response=null;

        try {
            response = alipayClient.execute(alipayRequest);
            System.out.println(JSON.toJSONString(response));
            if(!"10000".equals(response.getCode())){
//                return Result.error400(response.getSubMsg());
                throw new ApiException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new ApiException("更新失败");
        }

        return Result.success("");
    }
    @Login
    @GetMapping(value = "/caseFail",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "失败",notes = "")
    public Result caseFail(@RequestParam String id,@RequestParam String mes){

        PdCash pdCash = pdCashService.getById(id);
        pdCash.setPdcPaymentTime((int) (new Date().getTime()/1000));
        pdCash.setPdcPaymentState(2);
        pdCash.setPdcPaymentAdmin("admin");
        pdCash.setRefuseReason(mes);
        pdCashService.updateById(pdCash);
        if(!pdCashService.updateById(pdCash)){
            throw new ApiException("");
        }
        PdLog pdLog = pdLogService.getOne(new QueryWrapper<PdLog>().eq("lg_from_data",pdCash.getPdcId())
                                            .eq("lg_type","cash_apply")
        );
        if(pdLog == null){
            throw new ApiException("记录不存在");
        }
        pdLog.setLgType("cash_refuse");
        if(!pdLogService.updateById(pdLog)){
            throw new ApiException("更新失败");
        }

        User user = userService.getById(pdCash.getPdcMemberId());
        user.setAvailablePredeposit(user.getAvailablePredeposit().add(pdLog.getLgAvAmount()));
        user.setFreezePredeposit(user.getFreezePredeposit().subtract(pdLog.getLgAvAmount()));
        if(!userService.updateById(user)){
            throw new ApiException("更新失败");
        }

        return Result.success("");
    }


    @Login
    @PostMapping(value = "/getPointsList",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取积分充值列表",notes = "")
    public Result getPoints(@RequestBody MyPage page){
        IPage<Coin> data = customCoinService.page(page, new QueryWrapper<Coin>()
                .orderByDesc("add_time")
                .like("order_sn",page.getSearch1())
                .like("user_name",page.getSearch2())
                .like("pay_out_sn",page.getSearch3()));
        return Result.success(data);
    }

    @Login
    @PostMapping(value = "/getPointsFlow",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取积分流水列表",notes = "")
    public Result getPointsFlow(@RequestBody MyPage page){
        IPage<CoinLog> data = customCoinLogService.page(page, new QueryWrapper<CoinLog>()
                .orderByDesc("add_time")
                .like("log_user_name",page.getSearch1()));
        return Result.success(data);
    }

}
