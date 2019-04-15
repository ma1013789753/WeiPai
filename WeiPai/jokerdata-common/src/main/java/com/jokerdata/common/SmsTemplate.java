package com.jokerdata.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SmsTemplate {

    public void sendSms(String telephone,String code) throws ClientException {

        Map<String,String> map = new HashMap<>();
        map.put("code",code);

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4BqVjnag7u2F", "hGIvzzZ2Pwl9p08h1YUUVvaQt0pRqk");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", telephone);
        request.putQueryParameter("TemplateCode", "SMS_156350361");
        request.putQueryParameter("SignName", "weipro新媒体平台");
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));
        CommonResponse response = client.getCommonResponse(request);

    }

}
