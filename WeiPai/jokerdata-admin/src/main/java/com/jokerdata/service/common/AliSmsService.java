package com.jokerdata.service.common;

import org.springframework.stereotype.Service;

public interface AliSmsService {

    void sendSms(int type,String phone,String code);
}
