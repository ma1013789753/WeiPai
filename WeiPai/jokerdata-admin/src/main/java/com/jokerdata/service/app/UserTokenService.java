package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface UserTokenService extends IService<UserToken> {

    /**
     * 根据token 获取UserToken
     * @param token
     * @return
     */
    UserToken getUserByToken(String token);

    /**
     * 根据userId获取用户token
     * @param userId
     * @return
     */
    UserToken getTokenByUserId(Integer userId);
}
