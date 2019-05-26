package com.jokerdata.common.config;

import com.jokerdata.common.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.IdGenerator;

@Configuration
public class IdGeneratorConfig {

    private long workerId = 1;
    private long datacenterId = 1;
    private long sequence = 1;


    @Bean
    public IdWorker idWorkerInit() {
        IdWorker worker = new IdWorker(workerId,datacenterId,sequence);
        return worker;
    }
}
