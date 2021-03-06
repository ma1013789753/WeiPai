package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface UserService extends IService<User> {
    /**
     * 根据手机号获取User
     * @param mobile
     * @return
     */
    User getUserByMobile(String mobile);
}
