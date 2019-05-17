package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.mapper.app.generator.SmsMapper;
import com.jokerdata.service.app.SmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
    * 验证码表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class SmsImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {
    @Resource
    SmsMapper targetMapper;

    @Override
    public String valiteSms(String mobile, String code, String type) {

        QueryWrapper<Sms> wrapper = new QueryWrapper<>();
        wrapper.eq("sms_mobi",mobile)
                .eq("sms_content",code)
                .eq("sms_type",type)
                .orderByDesc("add_time");
        Sms sms = this.getOne(wrapper);
        if(sms == null){
            return "验证码不存在";
        }
        if((new Date().getTime()/1000-Long.parseLong(sms.getAddTime()))>5*60){
            return "验证码已过期";
        }
        return "1";
    }
}
