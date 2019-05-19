package com.jokerdata.controller.admin;

import com.jokerdata.common.JsonUtils;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.entity.admin.custom.ScheduleJob;
import com.jokerdata.service.admin.JobService;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * job任务控制器
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@RestController
@RequestMapping("/job")
@Api(value = "JobController",description  = "job调度")
@Slf4j
public class JobController {

    @Autowired
    private JobService jobService;


    @Login
    @GetMapping("/getList")
    @ApiOperation(value = "获取job列表",notes = "")
    public Result getJobList(){
        List<ScheduleJob> jobList = jobService.getAllJobs();
        return Result.success("success",jobList);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加/修改job",notes = "")
    public Result addJob(@RequestBody ScheduleJob scheduleJob){
        try {
            String res = jobService.saveOrupdate(scheduleJob);
            Result r = JsonUtils.jsonToPojo(res,Result.class);
            return r;
        } catch (Exception e) {
            log.error("add Job ex:", e);
            return Result.error500("add error!");
        }

    }

    @Login
    @PostMapping(value = "/run",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加/修改job",notes = "")
    public Result runJob(@RequestBody ScheduleJob scheduleJob){
        try {
            String res = jobService.runJob(scheduleJob);
            Result r = JsonUtils.jsonToPojo(res,Result.class);
            return r;
        } catch (Exception e) {
            log.error("run Job ex:", e);
            return Result.error500("run job error!");
        }

    }

    @Login
    @PostMapping(value = "/pause",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "暂停job",notes = "")
    public Result pauseJob(@RequestBody ScheduleJob scheduleJob){
        try {
            String res = jobService.pauseJob(scheduleJob);
            Result r = JsonUtils.jsonToPojo(res,Result.class);
            return r;
        } catch (Exception e) {
            log.error("pause Job ex:", e);
            return Result.error500("pause job error!");
        }

    }

    @Login
    @PostMapping(value = "/resume",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "恢复job",notes = "")
    public Result resumeJob(@RequestBody ScheduleJob scheduleJob){
        try {
            String res = jobService.resumeJob(scheduleJob);
            Result r = JsonUtils.jsonToPojo(res,Result.class);
            return r;
        } catch (Exception e) {
            log.error("resume Job ex:", e);
            return Result.error500("resume job error!");
        }

    }

    @Login
    @PostMapping(value = "/delete",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除job",notes = "")
    public Result deleteJob(@RequestBody ScheduleJob scheduleJob){
        try {
            String res = jobService.deleteJob(scheduleJob);
            Result r = JsonUtils.jsonToPojo(res,Result.class);
            return r;
        } catch (Exception e) {
            log.error("delete Job ex:", e);
            return Result.error500("delete job error!");
        }

    }

    @Login
    @PostMapping(value = "/getByName",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取指定job",notes = "")
    public Result getJob(@RequestBody Map<String, String> map){
        try {
            String res = jobService.getJobByName(map.get("jobName"));
            Result r = JsonUtils.jsonToPojo(res,Result.class);
            return r;
        } catch (Exception e) {
            log.error("get Job ex:", e);
            return Result.error500("get job error!");
        }

    }

}
