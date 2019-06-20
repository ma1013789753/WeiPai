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
import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.entity.app.generator.Config;
import com.jokerdata.entity.app.generator.User;
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
    private ShareService shareService;

    @Autowired
    private ShareLogService shareLogService;

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




}
