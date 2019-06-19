package com.jokerdata.common.config;

import com.jokerdata.common.job.AutowiringSpringBeanJobFactory;
import com.jokerdata.common.job.QuartzJobFactory;
import com.jokerdata.entity.admin.custom.ScheduleJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;


@Configuration
public class QuartzConfiguration  {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(DataSource dataSource, JobFactory jobFactory) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.afterPropertiesSet();
        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(jobFactory);
        Set<ScheduleJob> jobs = QuartzJobFactory.getInitAllJobs();
        for (ScheduleJob job : jobs) {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .withDescription(job.getDesc()).build();
                jobDetail.getJobDataMap().put("scheduleJob", job);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                        .getCronExpression());
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                        .getCronExpression());
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }

        scheduler.start();
        return scheduler;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
