package com.jokerdata.jokerdatapassageway.job;


import com.jokerdata.entity.admin.custom.ScheduleJob;
import com.jokerdata.jokerdatapassageway.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class QuartzJobFactory implements Job {

    public static Set<ScheduleJob> jobSet = new TreeSet();
    @Autowired
    private JobService jobService;

    public static Set<ScheduleJob> getInitAllJobs() {
        return jobSet;
    }

    /**
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        log.info("execute job:{} start", scheduleJob.getJobName());
        jobService.jobHandle(scheduleJob);
        log.info("execute job:{} end successfully",scheduleJob.getJobName());
    }

}
