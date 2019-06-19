package com.jokerdata.service.common;

import com.jokerdata.entity.admin.custom.ScheduleJob;

public interface JobService {

    void jobHandle(ScheduleJob scheduleJob);
}
