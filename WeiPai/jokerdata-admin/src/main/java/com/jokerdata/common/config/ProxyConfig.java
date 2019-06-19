package com.jokerdata.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="proxyip")
public class ProxyConfig {
    private String API_URL;
}
