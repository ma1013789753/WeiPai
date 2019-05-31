package com.jokerdata.controller.admin;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.common.utils.IdWorker;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.entity.app.custom.TaskCustom;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.service.app.TaskLogService;
import com.jokerdata.service.app.TaskService;
import com.jokerdata.service.app.UserAccountService;
import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import com.jokerdata.vo.TaskVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/task")
@Transactional(rollbackFor = ApiException.class)
public class TaskController {


    @Autowired
    TaskService taskService;

    @Autowired
    TaskLogService taskLogService;

    @Autowired
    IdWorker idWorker;

    @Autowired
    UserAccountService userAccountService;



    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<Task> data = taskService.page(page,new QueryWrapper<Task>()
                .orderByDesc("create_time")
                .like("name",page.getSearch1())
                .like("state",page.getSearch2())
        );
        return Result.success(data);
    }



    @PostMapping("/add")
    public Result add(TaskCustom taskCustom) {
        Task task = taskCustom;
        if (StringUtils.isEmpty(task.getId())){
            task.setCreateTime(new Date());
            task.setId(idWorker.nextId());
            List<TaskLog> taskLogs = new ArrayList<>();
            String[] ids = taskCustom.getIds().split(",");
            Arrays.asList(ids).forEach(id->{
                if(!StringUtils.isEmpty(id)){
                    TaskLog taskLog = new TaskLog();
                    taskLog.setTId(task.getId());
                    taskLog.setModifyTime(new Date());
                    taskLog.setCreateTime(new Date());
                    taskLog.setState(0);
                    taskLog.setAccountId(Long.parseLong(id));
                    taskLogs.add(taskLog);
                }
            });
            if(!taskLogService.saveBatch(taskLogs)){
                throw new ApiException("插入失败");
            }
        }else{
            task.setModifyTime(new Date());
            task.setCreateTime(null);
        }

        if(!taskService.saveOrUpdate(task)){
            throw new ApiException("插入失败");
        }

        return Result.success();
    }

    @Login
    @PostMapping("/getAccount")
    public Result getAccount(){

        List<UserAccount> data = userAccountService.list(new QueryWrapper<UserAccount>()
            .eq("acc_type",1)
                .eq("account_state",1)
                .orderByDesc("follow_num")
        );

        return Result.success(data);

    }

    @Login
    @PostMapping(value = "/getId",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id获取用户",notes = "")
    public Result getId(@RequestBody String id){

        if(StringUtils.isEmpty(id)){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }

        Task task = taskService.getById(id);
        if(task == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }

        return Result.success(task);
    }

    @GetMapping(value = "/task",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult task(String key,int type){
        User user = RequestHolder.getUser();

        List<TaskVo> datas = taskService.getListByUser(user.getUserId(),type);

        return ApiResult.success(datas);

    }


    @GetMapping(value = "/taskInfo",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult taskInfo(String key,String id){
        User user = RequestHolder.getUser();

        TaskVo data = taskService.getTaskById(id);

        return ApiResult.success(data);

    }

    @GetMapping(value = "/accept",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult accept(String key,String id){
        User user = RequestHolder.getUser();
        TaskLog taskLog = new TaskLog();
        taskLog.setId(id);
        taskLog.setState(1);

        taskLogService.updateById(taskLog);

        TaskVo data = taskService.getTaskById(id);
        return ApiResult.success(data);

    }

}

