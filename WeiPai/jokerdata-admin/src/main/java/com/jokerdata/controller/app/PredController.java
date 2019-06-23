package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.MD5;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.LoginParames;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.OrderSnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/Pred")
@Transactional(rollbackFor = ApiException.class)
public class PredController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private UserService userService;

    @Autowired
    private PdCashService pdCashService;

    @Autowired
    private PdLogService pdLogService;


    //生成单号
    @GetMapping(value = "/add_coin",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult add_coin(String key, String money) {
        User user = RequestHolder.getUser();

        if(StringUtils.isEmpty(money)){
            return ApiResult.error("参数错误");
        }
        int cash = Integer.parseInt(money);
        Config config = configService.getById(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Random random = new Random();
        int ends = random.nextInt(99);
        String no = "CO"+sdf.format(new Date())+String.format("%02d",ends);
        OrderSnVo orderSnVo = new OrderSnVo();
        orderSnVo.setCoin_amount(cash*Integer.parseInt(config.getConfigContent()));
        orderSnVo.setRate(config.getConfigContent());
        orderSnVo.setOrder_amount(money);
        orderSnVo.setOrder_sn(no);

        Coin coin = new Coin();
        coin.setOrderSn(orderSnVo.getOrder_sn());
        coin.setUserId(user.getUserId());
        coin.setUserName(user.getUserName());
        coin.setCoinAmount(orderSnVo.getCoin_amount());
        coin.setOrderAmount(new BigDecimal(orderSnVo.getOrder_amount()));
        coin.setPayState("0");
        coin.setPayTime(new Date().getTime()/1000);
        coin.setAddTime(new Date().getTime()/1000);
        if(!coinService.save(coin)){
            return ApiResult.error("保存失败");
        }
        return ApiResult.success(orderSnVo);
    }

    //提现
    @GetMapping(value = "/cash_add",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult cash_add(String key, String money,String alipay,String code,String name) {
        User user = RequestHolder.getUser();

        if(StringUtils.isEmpty(money)||StringUtils.isEmpty(alipay)||StringUtils.isEmpty(code)||StringUtils.isEmpty(name)){
            return ApiResult.error("参数错误");
        }
        if(!user.getUserPayPwd().equals(MD5.MD5Encode(code,"utf-8"))){
            return ApiResult.error("支付密码错误");
        }

        Config config = configService.getById(5);
        if(config == null){
            return ApiResult.error("参数错误");
        }
        double free = Double.parseDouble(money)*(1-Double.parseDouble(config.getConfigContent()));
        if(user.getAvailablePredeposit().doubleValue()<free){
            return ApiResult.error("余额不足");
        }

        user.setAvailablePredeposit(user.getAvailablePredeposit().subtract(new BigDecimal(free)));
        user.setFreezePredeposit(user.getFreezePredeposit().add(new BigDecimal(free)));
        if(!userService.updateById(user)){
            throw new ApiException("保存失败");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Random random = new Random();
        int ends = random.nextInt(99);
        String no = "CO"+sdf.format(new Date())+String.format("%02d",ends);

        PdCash pdCash = new PdCash();
        pdCash.setPdcSn(no);
        pdCash.setPdcMemberId(user.getUserId());
        pdCash.setPdcMemberName(user.getUserName());
        pdCash.setPdcAmount(new BigDecimal(free));
        pdCash.setPdcAddTime(new Date().getSeconds());
        pdCash.setPdcPaymentState(0);
        pdCash.setPdcBankNo(alipay);
        pdCash.setPdcBankName(name);
        if(!pdCashService.save(pdCash)){
            throw new ApiException("保存失败");
        }
        pdCash = pdCashService.getOne(new QueryWrapper<PdCash>().eq("pdc_add_time",pdCash.getPdcAddTime()));
        PdLog pdLog = new PdLog();
        pdLog.setLgMemberId(user.getUserId());
        pdLog.setLgMemberName(user.getUserName());
        pdLog.setLgType("cash_apply");
        pdLog.setLgAvAmount(new BigDecimal(free));
        pdLog.setLgFreezeAmount(new BigDecimal(free));
        pdLog.setLgAddTime((long) new Date().getSeconds());
        pdLog.setLgFromData(pdCash.getPdcId()+"");

        if(!pdLogService.save(pdLog)){
            throw new ApiException("保存失败");
        }

        return ApiResult.success("成功");
    }


    //钱包支付
    @GetMapping(value = "/money_pay",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult money_pay(String key, String order_sn,String code) {
        User user = RequestHolder.getUser();
        if(StringUtils.isEmpty(order_sn)||StringUtils.isEmpty(code)){
            return ApiResult.error("失败");
        }
        if(StringUtils.isEmpty(user.getUserPayPwd())){
            return ApiResult.error("请先设置支付密码");
        }
        if(!user.getUserPayPwd().equals(MD5.MD5Encode(code,"utf-8"))){
            return ApiResult.error("支付密码错误");
        }
        Coin coin = coinService.getOne(new QueryWrapper<Coin>().eq("order_sn",order_sn));
        if(coin == null){
            return ApiResult.error("查询失败");
        }

        return ApiResult.success("成功");
    }


    public static void main(String[] args) {
        System.out.println("MD5.MD5Encode(\"123456\",\"utf-8\") = " + MD5.MD5Encode("123456","utf-8"));
    }



}
