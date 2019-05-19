package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.custom.ScheduleJob;

import java.util.List;

public interface JobService {
    List<ScheduleJob> getAllJobs();
    String saveOrupdate(ScheduleJob scheduleJob) throws Exception;
    String runJob(ScheduleJob scheduleJob) throws Exception;
    String pauseJob(ScheduleJob scheduleJob) throws Exception;
    String resumeJob(ScheduleJob scheduleJob) throws Exception;
    String deleteJob(ScheduleJob scheduleJob) throws Exception;
    String getJobByName(String jobName) throws Exception;
}
