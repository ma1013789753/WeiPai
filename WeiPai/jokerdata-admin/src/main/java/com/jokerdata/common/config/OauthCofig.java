package com.jokerdata.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="wechat")
public class OauthCofig {
    private String AppID;
    private String AppSecret;
    private String Wechat_BaseURI;
}
