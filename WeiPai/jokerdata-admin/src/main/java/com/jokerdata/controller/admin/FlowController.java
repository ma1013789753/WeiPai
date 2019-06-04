package com.jokerdata.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.admin.*;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
public class FlowController {

    @Autowired
    private CustomPdCashService customPdCashService;

    @Autowired
    private CustomPdLogService customPdLogService;

    @Autowired
    private CustomCoinLogService customCoinLogService;

    @Autowired
    private CustomCoinService customCoinService;

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
