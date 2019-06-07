package com.jokerdata.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.admin.*;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  系统消息 前端控制器
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

   @Autowired
   private CustomPdCashService customPdCashService;

    @Autowired
    private CustomShareService customShareService;

    @Autowired
    private CustomUserAccountService customUserAccountService;

    @Autowired
    private CustomSystemMsgService customSystemMsgService;

    @Autowired
    private CustomFeedbackService customFeedbackService;


    @Login
    @PostMapping(value = "/getWithdrawCount",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取提现申请",notes = "")
    public Result getWithdrawCount(){
        int count = customPdCashService.count(new QueryWrapper<PdCash>()
                .eq("pdc_payment_state", 0));
        return Result.success(count);
    }

    @Login
    @PostMapping(value = "/getOriginalShareCount",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取原创申请",notes = "")
    public Result getOriginalShareCount(){
        int count = customShareService.count(new QueryWrapper<Share>()
                .eq("share_state", 1));
        return Result.success(count);
    }

    @Login
    @PostMapping(value = "/getPlatformAccCount",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "待审核第三方账号申请",notes = "")
    public Result getPlatformAccCount(@RequestParam int type){
        int count = 0;
        QueryWrapper queryWrapper = new QueryWrapper();
        switch (type){
            case 0:
                queryWrapper.eq("account_state",0);
                queryWrapper.eq("acc_type",0);
            case 1:
                queryWrapper.eq("account_state",0);
                queryWrapper.eq("acc_type",1);
            case 2:
                queryWrapper.eq("account_state",0);
                queryWrapper.eq("acc_type",2);
            case 3:
                queryWrapper.eq("account_state",0);
                queryWrapper.eq("acc_type",3);
        }
        count = customUserAccountService.count(queryWrapper);
        return Result.success(count);
    }

    @Login
    @PostMapping(value = "/getPendingFeedback",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取待处理反馈数",notes = "")
    public Result getPendingFeedback(){
        int count = customFeedbackService.count(new QueryWrapper<Feedback>()
                .eq("is_read", 0));
        return Result.success(count);
    }

    @Login
    @PostMapping(value = "/getSysMsg",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取系统消息列表",notes = "")
    public Result getSysMsg(@RequestBody MyPage page){
        IPage<SystemMsg> data = customSystemMsgService.getMsgPage(page);
        return Result.success(data);
    }

    @Login
    @PostMapping(value = "/delMsg",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除指定消息",notes = "")
    public Result delMsg(@RequestBody SystemMsg systemMsg){
        if(systemMsg == null ||systemMsg.getNoticeId() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        boolean res = customSystemMsgService.removeById(systemMsg.getNoticeId());
        if(!res){
            throw new MyException("删除失败", ConstCode.CODE_403);
        }
        return Result.success("删除成功");
    }

    @Login
    @PostMapping(value = "/addMsg",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "发布消息",notes = "")
    public Result addMsg(@RequestBody SystemMsg systemMsg){
        if(systemMsg == null ){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        systemMsg.setAddTime((new Date().getTime()/1000)+"");
        boolean res = customSystemMsgService.save(systemMsg);
        if(!res){
            throw new MyException("发布失败", ConstCode.CODE_403);
        }
        return Result.success("发布成功");
    }

    @Login
    @PostMapping(value = "/getFeedback",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取用户反馈列表",notes = "")
    public Result getFeedback(@RequestBody MyPage page){
        IPage<Feedback> data = customFeedbackService.page(page,new QueryWrapper<Feedback>()
                .orderByDesc("add_time")
                .like("user_name",page.getSearch1()));
        return Result.success(data);
    }

    @Login
    @PostMapping(value = "/handleFeedback",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "处理用户反馈",notes = "")
    public Result handleFeedback(@RequestBody Feedback feedback){
        if(feedback == null ||feedback.getFeedbackId() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        feedback.setIsRead(1);
        boolean res = customFeedbackService.updateById(feedback);
        if(!res){
            throw new MyException("操作失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }
}
