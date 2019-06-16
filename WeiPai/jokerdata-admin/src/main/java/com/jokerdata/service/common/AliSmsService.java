package com.jokerdata.service.common;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AliSmsService {

    void sendSms(int type,String phone,String code);
    void sendTask(List<String> ids);
}
