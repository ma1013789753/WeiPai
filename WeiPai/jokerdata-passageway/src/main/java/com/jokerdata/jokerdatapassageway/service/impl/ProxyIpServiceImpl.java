package com.jokerdata.jokerdatapassageway.service.impl;

import com.jokerdata.common.HttpClientUtil;
import com.jokerdata.common.JsonUtils;
import com.jokerdata.jokerdatapassageway.common.config.ProxyConfig;
import com.jokerdata.jokerdatapassageway.common.uitls.ProxyUtils;
import com.jokerdata.jokerdatapassageway.service.ProxyIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName ProxyIpServiceImpl
 * @Author Albert
 * @Description //代理IP获取 服务实现类
 * @Date 2019/5/6 16:18
 **/
@Slf4j
@Service
public class ProxyIpServiceImpl implements ProxyIpService {

    @Autowired
    private ProxyConfig proxyConfig;

    @Override
    public Map<String,Object> getProxyIp() {
        Map proIpMap = new HashMap();
        try {
            String ipstr = "";
            for(int i=0;i<3;i++){
                ipstr = HttpClientUtil.doGet(proxyConfig.getAPI_URL());
                log.info("get proxyIp api response: {} ",ipstr);
                if ("".equals(ipstr)) {
                    continue;
                }
                Map ip = JsonUtils.jsonToPojo(ipstr,Map.class);
                if ((int)ip.get("code") == 1) {
                    continue;
                }
                List data = (List)ip.get("data");
                Map ipdata = (Map)data.get(0);
                boolean ipavailable = checkIpavailable((String)ipdata.get("ip"), (int)ipdata.get("port"));
                if(ipavailable){
                    proIpMap.put("ip",ipdata.get("ip"));
                    proIpMap.put("port",ipdata.get("port"));
                    log.info("get proxyIp ip:{} ",proIpMap);
                    break;
                }

            }
        }catch (Exception e){
            log.error("get proxyIp error!",e.getMessage());
        }
        return proIpMap;

    }

    @Override
    public boolean checkIpavailable(String ip, int port) {
        boolean available= ProxyUtils.validateIp(ip,port, ProxyUtils.ProxyType.HTTPS);
        if(!available){
            available= ProxyUtils.validateIp(ip,port, ProxyUtils.ProxyType.HTTP);
        }
        return available;
    }
}
