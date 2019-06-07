package com.jokerdata.controller.admin;

import com.jokerdata.common.annotation.Login;
import com.jokerdata.service.admin.*;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *  数据图表 前端控制器
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/charts")
public class ChartsController {

   @Autowired
   private CustomerService customerService;

    @Autowired
    private CustomShareService customShareService;

    @Autowired
    private CustomShareLogService customShareLogService;

    @Login
    @PostMapping(value = "/getRegisterReport",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取平台注册用户",notes = "")
    public Result getRegisterReport(){
        List<Integer> registerReport = customerService.getRegisterReport();
        return Result.success(registerReport);
    }

    @Login
    @PostMapping(value = "/getShareReport",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取平台任务发布量",notes = "")
    public Result getShareReport(){
        List<Integer> shareReport = customShareService.getShareReport();
        return Result.success(shareReport);
    }

    @Login
    @PostMapping(value = "/getForwardReport",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取平台任务转发量",notes = "")
    public Result getForwardReport(){
        List<Integer> forwardReport = customShareLogService.getForwardReport();
        return Result.success(forwardReport);
    }

}
