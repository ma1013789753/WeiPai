package com.jokerdata.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.CoinLog;
import com.jokerdata.entity.app.generator.Shop;
import com.jokerdata.entity.app.generator.ShopHis;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.service.app.CoinLogService;
import com.jokerdata.service.app.ShopHisService;
import com.jokerdata.service.app.ShopService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import com.jokerdata.vo.ShopHisVo;
import com.mchange.v2.lang.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2019-08-15
 */
@RestController
@RequestMapping("/shop-his")
public class ShopHisController {

    @Autowired
    ShopHisService shopHisService;

    @Autowired
    UserService userService;

    @Autowired
    ShopService shopService;

    @Autowired
    CoinLogService coinLogService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<ShopHisVo> data = shopHisService.page(page,new QueryWrapper<ShopHis>()
                .orderByAsc("state")
        );
        List<ShopHisVo> dats = new ArrayList<>();
        for (int i = 0; i < data.getRecords().size(); i++) {

            ShopHis ShopHisVo = data.getRecords().get(i);
            ShopHisVo shopHisVo = new ShopHisVo();
            BeanUtils.copyProperties(ShopHisVo,shopHisVo);
            User user =  userService.getById(ShopHisVo.getUId());
            Shop shop = shopService.getById(ShopHisVo.getSId());
            shopHisVo.setShop(shop);
            shopHisVo.setUser(user);
            dats.add(shopHisVo);
        }

        data.setRecords(dats);
        return Result.success(data);
    }


    @Login
    @PostMapping(value = "/approve",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result approve(@RequestBody String id){

        ShopHis shopHis = shopHisService.getById(id);
        shopHis.setState("1");
        shopHisService.updateById(shopHis);
        return Result.success();
    }
    @Login
    @PostMapping(value = "/fail",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result fail(@RequestBody String id){

        ShopHis shopHis = shopHisService.getById(id);
        Shop shop = shopService.getById(shopHis.getSId());
        User user = userService.getById(shopHis.getUId());
        shopHis.setState("2");
        shopHisService.updateById(shopHis);

        Long time = new Date().getTime()/1000;
        CoinLog coinLog = new CoinLog();
        coinLog.setLogUserId(String.valueOf(user.getUserId()));
        coinLog.setLogUserName(user.getUserName());
        coinLog.setLogType("shop_refunt");
        coinLog.setLogAvCoin(shop.getCost());
        coinLog.setLogMark("返还商品积分");
        coinLog.setAddTime(time);
        if(!coinLogService.save(coinLog)){
            throw new ApiException("更新失败");
        }

        user.setUserCoin(user.getUserCoin()+shop.getCost().intValue());

        if(!userService.updateById(user)){
            throw new ApiException("更新失败");
        }


        return Result.success();
    }
}

