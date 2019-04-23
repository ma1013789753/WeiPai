package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.mapper.app.generator.SmsMapper;
import com.jokerdata.service.app.SmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 验证码表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-23
 */
@Service
public class SmsImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {
    @Resource
    SmsMapper targetMapper;

    @Override
    public MyPage<Sms> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
