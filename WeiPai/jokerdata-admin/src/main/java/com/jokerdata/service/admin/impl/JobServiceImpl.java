package com.jokerdata.service.admin.impl;

import com.jokerdata.common.HttpClientUtil;
import com.jokerdata.common.JsonUtils;
import com.jokerdata.common.config.PassagewayConfig;
import com.jokerdata.entity.admin.custom.ScheduleJob;
import com.jokerdata.service.admin.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private PassagewayConfig passagewayConfig;


    @Override
    public List<ScheduleJob> getAllJobs() {
        String jsonStr = HttpClientUtil.doGet(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getGET_JOBS_URL());
        HashMap map = JsonUtils.jsonToPojo(jsonStr, HashMap.class);
        List<ScheduleJob> jobList = (List<ScheduleJob>)map.get("data");
        return jobList;
    }

    @Override
    public String saveOrupdate(ScheduleJob scheduleJob) throws Exception {
        String jsonObj = JsonUtils.objectToJson(scheduleJob);
        String res = HttpClientUtil.doPostJson(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getADD_JOB_URL(),jsonObj);
        return res;
    }

    @Override
    public String runJob(ScheduleJob scheduleJob) throws Exception {
        String jsonObj = JsonUtils.objectToJson(scheduleJob);
        String res =  HttpClientUtil.doPostJson(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getRUN_JOB_URL(),jsonObj);
        return res;
    }

    @Override
    public String pauseJob(ScheduleJob scheduleJob) throws Exception {
        String jsonObj = JsonUtils.objectToJson(scheduleJob);
        String res =  HttpClientUtil.doPostJson(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getPAUSE_JOB_URL(),jsonObj);
        return res;
    }

    @Override
    public String resumeJob(ScheduleJob scheduleJob) throws Exception {
        String jsonObj = JsonUtils.objectToJson(scheduleJob);
        String res =  HttpClientUtil.doPostJson(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getRESUME_JOB_URL(),jsonObj);
        return res;
    }

    @Override
    public String deleteJob(ScheduleJob scheduleJob) throws Exception {
        String jsonObj = JsonUtils.objectToJson(scheduleJob);
        String res =  HttpClientUtil.doPostJson(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getDELETE_JOB_URL(),jsonObj);
        return res;
    }

    @Override
    public String getJobByName(String jobName) throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("jobName",jobName);
        String res = HttpClientUtil.doGet(passagewayConfig.getPASSAGEWAY_BASE_URL() + passagewayConfig.getGET_JOB_URL(),params);
        return res;
    }
}
