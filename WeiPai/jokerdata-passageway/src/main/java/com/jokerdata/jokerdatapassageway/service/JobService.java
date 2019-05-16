package com.jokerdata.jokerdatapassageway.service;

import com.jokerdata.entity.admin.custom.ScheduleJob;

public interface JobService {

    void jobHandle(ScheduleJob scheduleJob);
}
