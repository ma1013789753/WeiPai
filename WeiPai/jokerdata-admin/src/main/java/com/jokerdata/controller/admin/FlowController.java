package com.jokerdata.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.admin.*;
import com.jokerdata.service.app.PdCashService;
import com.jokerdata.service.app.PdLogService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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
        pdCash.setPdcPaymentTime((int) (new Date().getTime()/1000));
        pdCash.setPdcPaymentState(1);
        pdCash.setPdcPaymentAdmin("admin");
        pdCashService.updateById(pdCash);
        if(!pdCashService.updateById(pdCash)){
            throw new ApiException("");
        }
        PdLog pdLog = pdLogService.getOne(new QueryWrapper<PdLog>().eq("lg_from_data",pdCash.getPdcId()));
        if(pdLog == null){
            throw new ApiException("记录不存在");
        }
        pdLog.setLgType("cash_pay");
        if(!pdLogService.updateById(pdLog)){
            throw new ApiException("更新失败");
        }
        return Result.success("");
    }
    @Login
    @GetMapping(value = "/caseFail",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "失败",notes = "")
    public Result caseFail(@RequestParam String id,@RequestParam String msg){

        PdCash pdCash = pdCashService.getById(id);
        pdCash.setPdcPaymentTime((int) (new Date().getTime()/1000));
        pdCash.setPdcPaymentState(2);
        pdCash.setPdcPaymentAdmin("admin");
        pdCash.setRefuseReason(msg);
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
        user.setAvailablePredeposit(user.getAvailablePredeposit().add(pdCash.getPdcAmount()));
        user.setFreezePredeposit(user.getFreezePredeposit().subtract(pdCash.getPdcAmount()));
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
