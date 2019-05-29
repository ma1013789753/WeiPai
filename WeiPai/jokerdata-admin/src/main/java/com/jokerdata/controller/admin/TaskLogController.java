package com.jokerdata.controller.admin;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.Task;
import com.jokerdata.entity.app.generator.TaskLog;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.service.app.TaskLogService;
import com.jokerdata.service.app.TaskService;
import com.jokerdata.service.app.UserAccountService;
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

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){

        IPage<TaskVo> data = taskLogService.getPage(page);
        return Result.success(data);
    }

}

