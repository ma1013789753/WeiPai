package com.jokerdata.jokerdatapassageway.controller;

import com.jokerdata.entity.admin.custom.ScheduleJob;

import com.jokerdata.jokerdatapassageway.service.ScheduleJobService;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 定时任务控制器
 * </p>
 * @author aozhang
 * @since 2019-5-1
 */
@RestController
@RequestMapping("/job")
@Api(value = "JobController",description  = "job控制")
@Slf4j
public class JobController {

    @Autowired
    ScheduleJobService scheduleJobService;

    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加定时任务",notes = "")
    public Result addScheduleJob(@RequestBody ScheduleJob job) {
        log.info("add/update job params {}",job);
        try {
            scheduleJobService.saveOrUpdateShareJob(job);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("add/update Job ex:", e);
            return Result.error500("操作失败");
        }

    }

    @RequestMapping("/getAllJobs")
    public Result getAllJobs() throws SchedulerException {
        List<ScheduleJob> jobList = scheduleJobService.getAllJobs();
        return Result.success("success",jobList);
    }

    @PostMapping(value = "/run",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "手动运行定时job",notes = "")
    public Result runScheduleJob(@RequestBody ScheduleJob job) {
        log.info("run job params {}",job);
        try {
            scheduleJobService.runJobOnce(job);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("runJob ex:", e);
            return Result.error500("操作失败");
        }

    }

    @PostMapping(value = "/pause",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "暂停job",notes = "")
    public Result pauseScheduleJob(@RequestBody ScheduleJob job) {
        log.info("pause job params {}",job);
        try {
            scheduleJobService.pauseJob(job);
            return Result.success("暂停成功");
        } catch (Exception e) {
            log.error("pauseJob ex:", e);
            return Result.error500("操作失败");
        }

    }

    @PostMapping(value = "/resume",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "恢复job",notes = "")
    public Result resumeScheduleJob(@RequestBody ScheduleJob job) {
        log.info("resume job params {}",job);
        try {
            scheduleJobService.resumeJob(job);
            return Result.success("执行成功");
        } catch (Exception e) {
            log.error("resume Job ex:", e);
            return Result.error500("执行失败");
        }
    }

    @PostMapping(value = "/delete",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除job",notes = "")
    public Result deleteScheduleJob(@RequestBody ScheduleJob job) {
        log.info("delete job params {}",job);
        try {
            scheduleJobService.deleteJob(job);
            return Result.success("执行成功");
        } catch (Exception e) {
            log.error("delete Job ex:", e);
            return Result.error500("执行失败");
        }
    }

    @GetMapping(value = "/getByName")
    @ApiOperation(value = "获取指定job",notes = "")
    public Result getScheduleJobByName(String jobName) {
        log.info("job params {}",jobName);
        try {
            ScheduleJob job = scheduleJobService.getJobByName(jobName);
            return Result.success("执行成功",job);
        } catch (Exception e) {
            log.error("get Job ex:", e);
            return Result.error500("执行失败");
        }
    }
}
