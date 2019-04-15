package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.mapper.app.generator.SmsCustomMapper;
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
 * @since 2019-04-15
 */
@Service
public class SmsImpl extends ServiceImpl<SmsCustomMapper, Sms> implements SmsService {
    @Resource
    SmsCustomMapper targetMapper;

    @Override
    public MyPage<Sms> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
