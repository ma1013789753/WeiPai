package com.jokerdata.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.admin.CustomShareService;
import com.jokerdata.service.admin.CustomShareTagService;
import com.jokerdata.service.app.PdLogService;
import com.jokerdata.service.app.ShareLogService;
import com.jokerdata.service.app.ShareService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.PShareLog;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/shareLog")
@Transactional(rollbackFor = ApiException.class)
public class PLogController {


    @Autowired
    private ShareService shareService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareLogService shareLogService;

    @Autowired
    private PdLogService pdLogService;


    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<PShareLog> data =  shareService.getPPage(page);

        return Result.success(data);
    }




    @Login
    @PostMapping(value = "/approve",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审核通过",notes = "")
    public Result approve(@RequestBody PShareLog pShareLog){

        ShareLog log = pShareLog.getShareLog();
        log.setIsPass(1);
        log.setCheckTime(new Date().getTime()/1000+"");
        if(!shareLogService.updateById(log)){
           throw new ApiException("更新失败");
        }

        PdLog pdLog = pShareLog;
        pdLog.setLgType("task_income");

        if(!pdLogService.updateById(pdLog)){
            throw new ApiException("更新失败");
        }

        User user = userService.getById(pShareLog.getShareLog().getUserId());
        user.setAvailablePredeposit(user.getAvailablePredeposit().add(pdLog.getLgAvAmount()));

        if(!userService.updateById(user)){
            throw new ApiException("金额更新失败");
        }


        //是否完成
        Share share = pShareLog.getShare();
        share.setShareNumTrue(share.getShareNumTrue()+1);
        if(share.getShareNumTrue()>=share.getShareNum()){

            PdLog pdCash = pdLogService.getOne(new QueryWrapper<PdLog>()
                    .eq("lg_type","task_freeze")
                    .eq("lg_desc",share.getShareId())
            );
            if(pdCash==null){
                throw new ApiException("不存在");
            }
            //查看花了多少
            BigDecimal count = pdLogService.getAllCount(share.getShareId());
            if(count == null ){
                throw new ApiException("错误");
            }
            //退还金额
            PdLog refunt = new PdLog();
            refunt.setLgType("refund");
            refunt.setLgMemberId(pdCash.getLgMemberId());
            refunt.setLgMemberName(pdCash.getLgMemberName());
            refunt.setLgAddTime(new Date().getTime()/1000);
            refunt.setLgAvAmount(new BigDecimal(-pdCash.getLgAvAmount().doubleValue()).subtract(count));
            if(!pdLogService.save(refunt)){
                throw new ApiException("更新失败");
            }
            User target = userService.getById(pShareLog.getShare().getUserId());
            target.setAvailablePredeposit(target.getAvailablePredeposit().add(refunt.getLgAvAmount()));

            if(!userService.updateById(target)){
                throw new ApiException("更新失败");
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
    @ApiOperation(value = "审核通过",notes = "")
    public Result approveFail(@RequestBody PShareLog pShareLog){
        ShareLog log = pShareLog.getShareLog();
        log.setIsPass(2);
        if(!shareLogService.updateById(log)){
            throw new ApiException("更新失败");
        }

        PdLog pdLog = pShareLog;
        pdLog.setLgType("check_false");

        if(!pdLogService.updateById(pdLog)){
            throw new ApiException("更新失败");
        }

        Share share = pShareLog.getShare();
        share.setHaveSharedNum(share.getHaveSharedNum()-1);

        if(!shareService.updateById(share)){
            throw new ApiException("更新失败");
        }

        return Result.success();
    }


}
