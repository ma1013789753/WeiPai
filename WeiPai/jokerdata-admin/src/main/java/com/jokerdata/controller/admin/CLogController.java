package com.jokerdata.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.CShareLog;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.PShareLog;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/shareCLog")
@Transactional(rollbackFor = ApiException.class)
public class CLogController {


    @Autowired
    private ShareService shareService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareLogService shareLogService;

    @Autowired
    private CoinLogService coinLogService;


    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<CShareLog> data =  shareService.getCPage(page);

        return Result.success(data);
    }




    @Login
    @PostMapping(value = "/approve",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审核通过",notes = "")
    public Result approve(@RequestBody CShareLog cShareLog){

        ShareLog log = cShareLog.getShareLog();
        log.setIsPass(1);
        log.setCheckTime(new Date().getTime()/1000+"");
        if(!shareLogService.updateById(log)){
           throw new ApiException("更新失败");
        }
        CoinLog coinLog = cShareLog;
        coinLog.setLogType("task_income");
        if(!coinLogService.updateById(coinLog)){
            throw new ApiException("更新失败");
        }
        //更新用户积分
        User user = userService.getById(cShareLog.getShareLog().getUserId());
        user.setUserCoin(user.getUserCoin()+coinLog.getLogAvCoin().intValue());
        user.setCoinTotal(user.getCoinTotal()+coinLog.getLogAvCoin().intValue());
        if(!userService.updateById(user)){
            throw new ApiException("金额更新失败");
        }
        //是否完成
        Share share = cShareLog.getShare();
        if(share.getShareState().equals("4")){
            return Result.success();
        }
        if(share.getHaveSharedNum()>=share.getShareNum()){

            CoinLog coinCash = coinLogService.getOne(new QueryWrapper<CoinLog>()
                    .eq("log_type","task_freeze")
                    .eq("log_mark",share.getShareId())
            );
            if(coinCash==null){
                throw new ApiException("不存在");
            }

            //设置结束
            share.setShareState("3");
            //更新用户金额
            if(!shareService.updateById(share)){
                throw new ApiException("更新失败");
            }
        }
        return Result.success();
    }

    @Login
    @PostMapping(value = "/approveFail",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审核失败",notes = "")
    public Result approveFail(@RequestBody CShareLog cShareLog){
        ShareLog log = cShareLog.getShareLog();
        log.setIsPass(2);
        if(!shareLogService.updateById(log)){
            throw new ApiException("更新失败");
        }

        CoinLog coinLog = cShareLog;
        coinLog.setLogType("check_false");

        if(!coinLogService.updateById(coinLog)){
            throw new ApiException("更新失败");
        }

        Share share = cShareLog.getShare();
        if(share.getShareState().equals("4")){
            return Result.success();
        }
        share.setHaveSharedNum(share.getHaveSharedNum()-1);

        if(!shareService.updateById(share)){
            throw new ApiException("更新失败");
        }

        return Result.success();
    }


}
