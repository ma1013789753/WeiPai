package com.jokerdata.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="passageway")
public class PassagewayConfig {
    private String PASSAGEWAY_BASE_URL;
    private String GET_JOBS_URL;
    private String ADD_JOB_URL;
    private String RUN_JOB_URL;
    private String PAUSE_JOB_URL;
    private String RESUME_JOB_URL;
    private String DELETE_JOB_URL;
    private String GET_JOB_URL;
}
