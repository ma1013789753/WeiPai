package com.jokerdata.jokerdatapassageway.service;


import com.jokerdata.entity.admin.custom.ScheduleJob;
import org.quartz.SchedulerException;

import java.util.List;

public interface ScheduleJobService {
    void addShareJob(ScheduleJob scheduleJob) throws Exception;
    void saveOrUpdateShareJob(ScheduleJob scheduleJob) throws Exception;
    void runJobOnce(ScheduleJob scheduleJob) throws SchedulerException;
    void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;
    void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;
    void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
    ScheduleJob getJobByName(String jobName) throws SchedulerException;
    List<ScheduleJob> getAllJobs();
}
