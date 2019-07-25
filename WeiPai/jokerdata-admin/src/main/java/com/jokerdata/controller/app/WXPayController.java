package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.config.AlipayConfig;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.service.app.CoinService;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Notify")
@Transactional(rollbackFor = ApiException.class)
public class WXPayController {
    @Autowired
    private CoinService coinService;

    @GetMapping(value = "/alipay_orderinfo",produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResult getAliPayOrderStr(String order_sn) {

        Coin coin = coinService.getOne(new QueryWrapper<Coin>().eq("order_sn",order_sn));

        //最终返回加签之后的，app需要传给支付宝app的订单信息字符串
        String orderString = "";



        Map<String,String> data = new HashMap<>();
        data.put("orderinfo",orderString);
        return ApiResult.success(data);
    }

}
