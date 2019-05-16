package com.jokerdata.jokerdatapassageway.service;

import java.util.Map;

public interface ProxyIpService {
    Map<String,Object> getProxyIp();
    boolean checkIpavailable(String ip, int port);
}
