package com.jokerdata.service.common.impl;

import com.jokerdata.entity.admin.custom.ScheduleJob;
import com.jokerdata.service.common.JobService;
import com.jokerdata.service.common.ToutiaoService;
import com.jokerdata.service.common.WeiboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private WeiboService weiboService;

    @Autowired
    private ToutiaoService toutiaoService;
    @Override
    public void jobHandle(ScheduleJob scheduleJob) {
        switch (scheduleJob.getJobType()) {
            case "share":
                shareJob(scheduleJob);
                break;
            default:
                new Exception("msgType error!");
        }
    }

    private void shareJob(ScheduleJob scheduleJob) {
        log.info("Job {} start:",scheduleJob.getJobName());
        switch (scheduleJob.getJobName().toLowerCase()) {
            case "share-weibo":
                weiboService.crawlingShareData();
                break;
            case "share-toutiao":
                toutiaoService.crawlingToutiaoShare();
                break;
            case "share-income":
                weiboService.crawlingShareData();
                break;
            default:
                new Exception(" job isn't exist!");
        }

    }
}
