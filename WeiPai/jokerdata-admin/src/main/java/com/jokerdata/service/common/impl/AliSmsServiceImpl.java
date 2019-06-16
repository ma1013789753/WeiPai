package com.jokerdata.service.common.impl;

import com.aliyuncs.exceptions.ClientException;
import com.jokerdata.common.SmsTemplate;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.mapper.app.custom.SmsCustomMapper;
import com.jokerdata.mapper.app.generator.UserMapper;
import com.jokerdata.service.common.AliSmsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AliSmsServiceImpl implements AliSmsService {

    @Resource
    SmsTemplate smsTemplate;

    @Resource
    SmsCustomMapper smsCustomMapper;

    @Resource
    UserMapper userMapper;

    @Async
    @Override
    public void sendSms(int type, String phone, String code) {

        try {
            smsTemplate.sendSms(phone,code);
        } catch (ClientException e) {

            throw new ApiException("验证码发送失败！");
        }
        Sms sms = new Sms();
        sms.setAddTime(String.valueOf(new Date().getTime()/1000));
        sms.setSmsContent(code);
        sms.setSmsMobi(phone);
        sms.setSmsType(type);
        smsCustomMapper.insert(sms);

    }

    @Override
    @Async
    public void sendTask(List<String> ids) {
        List<User> datas = userMapper.selectListByAccountId(ids);
        datas.forEach(user -> {
            try {
                smsTemplate.sendTask(user.getUserMobile());
            } catch (ClientException e) {
                e.printStackTrace();
            }
        });
    }


}
