package com.jokerdata.service.common.impl;

import com.aliyuncs.exceptions.ClientException;
import com.jokerdata.common.SmsTemplate;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.mapper.app.generator.SmsCustomMapper;
import com.jokerdata.service.common.AliSmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Service
public class AliSmsServiceImpl implements AliSmsService {

    @Resource
    SmsTemplate smsTemplate;

    @Resource
    SmsCustomMapper smsCustomMapper;

    @Override
    public void sendSms(int type, String phone, String code) {

        try {
            smsTemplate.sendSms(phone,code);
        } catch (ClientException e) {

            throw new ApiException("验证码发送失败！");
        }
        Sms sms = new Sms();
        sms.setAdd_time(String.valueOf(new Date().getTime()/1000));
        sms.setSms_content(code);
        sms.setSms_mobi(phone);
        sms.setSms_type(type);
        smsCustomMapper.insert(sms);

    }


}
