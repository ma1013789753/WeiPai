package com.jokerdata.controller.admin;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import com.jokerdata.vo.TaskVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/taskLog")
@Transactional(rollbackFor = ApiException.class)
public class TaskLogController {

    @Autowired
    TaskLogService taskLogService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    PdLogService pdLogService;

    @Autowired
    UserService userService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<TaskVo> data = taskLogService.getPage(page);
        return Result.success(data);
    }


    //审核成功
    @Login
    @PostMapping(value = "/approve",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result approve(@RequestBody TaskVo taskVo){
        TaskLog taskLog = new TaskLog();
        taskLog.setId(taskVo.getId());
        taskLog.setState(3);
        if(!taskLogService.updateById(taskLog)){
           throw  new ApiException("更新失败");
        }

        PdLog pdLog =pdLogService.getOne(new QueryWrapper<PdLog>().eq("lg_from_data",taskLog.getId()));
        if(pdLog == null){
            throw new ApiException("查询失败");
        }
        pdLog.setLgType("task_check_fail");
        if(!pdLogService.updateById(pdLog)){
            throw  new ApiException("更新失败");
        }

        User user = userService.getById(pdLog.getLgMemberId());
        user.setAvailablePredeposit(user.getAvailablePredeposit().add(pdLog.getLgAvAmount()));

        if(!userService.updateById(user)){
            throw  new ApiException("更新失败");
        }
        return Result.success();
    }

    //审核失败
    @Login
    @PostMapping(value = "/approveFail",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result approveFail(@RequestBody TaskVo taskVo){
        TaskLog taskLog = new TaskLog();
        taskLog.setId(taskVo.getId());
        taskLog.setState(4);
        if(!taskLogService.updateById(taskLog)){
            throw  new ApiException("更新失败");
        }

        PdLog pdLog =pdLogService.getOne(new QueryWrapper<PdLog>().eq("lg_from_data",taskLog.getId()));
        if(pdLog == null){
            throw new ApiException("查询失败");
        }
//        pdLog.setLgType("check_false");
        if(!pdLogService.removeById(pdLog)){
            throw  new ApiException("更新失败");
        }
        return Result.success();
    }

}

