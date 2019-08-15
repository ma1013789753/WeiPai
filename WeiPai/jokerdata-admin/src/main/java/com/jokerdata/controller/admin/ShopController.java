package com.jokerdata.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.admin.custom.SysUserCustom;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.app.CoinLogService;
import com.jokerdata.service.app.ShopHisService;
import com.jokerdata.service.app.ShopService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import com.jokerdata.vo.ShopVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;
    @Autowired
    CoinLogService coinLogService;
    @Autowired
    UserService userService;
    @Autowired
    ShopHisService shopHisService;





    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<Shop> data = shopService.page(page,new QueryWrapper<Shop>()
                .orderByDesc("release_time")
                .eq("del",0)
                .like("title",page.getSearch1())
        );
        return Result.success(data);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "增加",notes = "")
    public Result getPage(@RequestBody Shop shop){

        shop.setReleaseTime(new Date());
        shopService.saveOrUpdate(shop);
        return Result.success();
    }


    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除",notes = "")
    public Result del(@RequestBody Shop shop){

        shop.setDel(true);
        shopService.saveOrUpdate(shop);
        return Result.success();
    }


    @GetMapping(value = "/getList",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public ApiResult getList(){

        List<Shop> data = shopService.list(new QueryWrapper<Shop>()
                .orderByDesc("release_time")
                .eq("del",0)
        );
        return ApiResult.success(data);
    }

    @GetMapping(value = "/getSid",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取详情",notes = "")
    public ApiResult getSid(long id){

        Shop shop = shopService.getById(id);
        return ApiResult.success(shop);
    }

    @Auth(value = true)
    @GetMapping(value = "/hold",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取详情",notes = "")
    public ApiResult hold(String key,String name,String address,String id){
        User user = RequestHolder.getUser();


        Shop shop = shopService.getById(id);
        if(shop==null){
            throw  new ApiException("获取异常");
        }

        if(user.getUserCoin()<shop.getCost().intValue()){
            throw  new ApiException("余额不足请前往充值");
        }


        Long time = new Date().getTime()/1000;
        CoinLog coinLog = new CoinLog();
        coinLog.setLogUserId(String.valueOf(user.getUserId()));
        coinLog.setLogUserName(user.getUserName());
        coinLog.setLogType("shop_buy");
        coinLog.setLogAvCoin(shop.getCost());
        coinLog.setLogMark("购买商品积分");
        coinLog.setAddTime(time);
        if(!coinLogService.save(coinLog)){
            throw new ApiException("更新失败");
        }
        coinLog = coinLogService.getOne(new QueryWrapper<CoinLog>().eq("add_time",time));

        user.setUserCoin(user.getUserCoin()-shop.getCost().intValue());

        if(!userService.updateById(user)){
            throw new ApiException("更新失败");
        }


        ShopHis shopHis = new ShopHis();
        shopHis.setName(name);
        shopHis.setAddress(address);
        shopHis.setCId(Long.valueOf(coinLog.getLogId()));
        shopHis.setSId(shop.getId());
        shopHis.setUId(Long.valueOf(user.getUserId()));
        shopHis.setUpdateTime(new Date());

        if(!shopHisService.save(shopHis)){
            throw new ApiException("更新失败");
        }


        return ApiResult.success(shop);
    }
}

