package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.mapper.app.generator.UserTokenMapper;
import com.jokerdata.service.app.UserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class UserTokenImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    @Resource
    UserTokenMapper targetMapper;

    @Override
    public MyPage<UserToken> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
