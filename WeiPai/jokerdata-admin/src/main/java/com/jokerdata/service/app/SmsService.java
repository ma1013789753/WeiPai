package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 验证码表 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface SmsService extends IService<Sms> {

     MyPage<Sms> selectPage(MyPage page);
}
