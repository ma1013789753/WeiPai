package com.jokerdata.service.common;

import java.util.Map;

public interface ProxyIpService {
    Map<String,Object> getProxyIp();
    boolean checkIpavailable(String ip, int port);
}
