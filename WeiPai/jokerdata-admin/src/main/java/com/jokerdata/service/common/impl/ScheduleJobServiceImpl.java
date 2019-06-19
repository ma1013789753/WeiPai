package com.jokerdata.service.common.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jokerdata.common.job.QuartzJobFactory;
import com.jokerdata.entity.admin.custom.ScheduleJob;
import com.jokerdata.service.common.ScheduleJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @ClassName ScheduleJobServiceImpl
 * @Author Albert
 * @Description //任务调度服务实现类
 * @Date 2019/5/16 17:34
 **/
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Resource
    @Qualifier("scheduler")
    private Scheduler scheduler;

    @Override
    public void saveOrUpdateShareJob(ScheduleJob scheduleJob) throws Exception {
        Preconditions.checkNotNull(scheduleJob, "job is null");
        if (StringUtils.isEmpty(scheduleJob.getJobId())) {
            addShareJob(scheduleJob);
        } else {
            deleteJob(scheduleJob);
            addShareJob(scheduleJob);
        }
    }

    @Override
    public void runJobOnce(ScheduleJob scheduleJob) throws SchedulerException {
        checkIsNull(scheduleJob);
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    @Override
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        checkIsNull(scheduleJob);
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        checkIsNull(scheduleJob);
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    @Override
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        checkIsNull(scheduleJob);
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    @Override
    public ScheduleJob getJobByName(String jobName) throws SchedulerException {
        List<ScheduleJob> jobList = Lists.newArrayList();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeySet) {
                if(jobKey.getName().equals(jobName)){
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                        ScheduleJob scheduleJob = new ScheduleJob();
                        this.wrapScheduleJob(scheduleJob, scheduler, jobKey, trigger);
                        jobList.add(scheduleJob);
                    }
                }

            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList.get(0);

    }


    @Override
    public void addShareJob(ScheduleJob scheduleJob) throws Exception {
        checkIsNull(scheduleJob);
        Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getCronExpression()), "CronExpression is null");
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger != null) {
            throw new Exception("job already exists!");
        }

        scheduleJob.setJobId(String.valueOf(QuartzJobFactory.jobSet.size() + 1));
        QuartzJobFactory.jobSet.add(scheduleJob);

        JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
        jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }



    @Override
    public List<ScheduleJob> getAllJobs() {
        List<ScheduleJob> jobList = Lists.newArrayList();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeySet) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduleJob scheduleJob = new ScheduleJob();
                    this.wrapScheduleJob(scheduleJob, scheduler, jobKey, trigger);
                    jobList.add(scheduleJob);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    private void wrapScheduleJob(ScheduleJob scheduleJob, Scheduler scheduler, JobKey jobKey, Trigger trigger) {
        try {
            scheduleJob.setJobName(jobKey.getName());
            scheduleJob.setJobGroup(jobKey.getGroup());

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            ScheduleJob job = (ScheduleJob) jobDetail.getJobDataMap().get("scheduleJob");
            scheduleJob.setDesc(job.getDesc());
            scheduleJob.setJobId(job.getJobId());
            scheduleJob.setJobType(job.getJobType());
            scheduleJob.setContent(job.getContent());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            scheduleJob.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                scheduleJob.setCronExpression(cronExpression);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void checkIsNull(ScheduleJob scheduleJob) {
        Preconditions.checkNotNull(scheduleJob, "job is null");
        Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getJobName()), "jobName is null");
        Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getJobGroup()), "jobGroup is null");
    }
}
