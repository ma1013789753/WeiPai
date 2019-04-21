package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.UserToken;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface UserTokenService extends IService<UserToken> {

     MyPage<UserToken> selectPage(MyPage page);
}
