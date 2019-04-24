package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.mapper.app.generator.SmsMapper;
import com.jokerdata.service.app.SmsService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

}
