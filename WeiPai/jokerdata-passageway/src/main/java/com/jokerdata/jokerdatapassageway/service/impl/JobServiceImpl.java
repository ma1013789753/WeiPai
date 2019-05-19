package com.jokerdata.jokerdatapassageway.service.impl;

import com.jokerdata.entity.admin.custom.ScheduleJob;
import com.jokerdata.jokerdatapassageway.service.JobService;
import com.jokerdata.jokerdatapassageway.service.WeiboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private WeiboService weiboService;

    @Override
    public void jobHandle(ScheduleJob scheduleJob) {
        switch (scheduleJob.getJobType()) {
            case "share":
                shareJob(scheduleJob);
                break;
            default:
                new Exception("type error!");
        }
    }

    private void shareJob(ScheduleJob scheduleJob) {
        log.info("Job {} start:",scheduleJob.getJobName());
        switch (scheduleJob.getJobName().toLowerCase()) {
            case "share-weibo":
                weiboService.crawlingShareData();
                break;
            case "share-toutiao":
                weiboService.crawlingShareData();
                break;
            case "share-income":
                weiboService.crawlingShareData();
                break;
            default:
                new Exception(" job isn't exist!");
        }

    }
}