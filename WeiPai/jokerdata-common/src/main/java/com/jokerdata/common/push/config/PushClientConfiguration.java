package com.jokerdata.common.push.config;

import cn.jpush.api.JPushClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息推送
 * @author :zhuys
 * @date: 2019/4/27 15:11
 */
@Configuration
public class PushClientConfiguration {

    Logger logger = LoggerFactory.getLogger(PushClientConfiguration.class);

    @Value("${jpush.MasterSecret}")
    private String secret;

    @Value("${jpush.AppKey}")
    private String appkey;



    /**
     * 司机端推送
     * @return
     */
    @Bean("pushClient")
    public JPushClient pushDriverClientFactory() {
        logger.error("appkey",appkey);
        logger.error("MasterSecret",secret);
        return new JPushClient(secret, appkey);
    }


}
