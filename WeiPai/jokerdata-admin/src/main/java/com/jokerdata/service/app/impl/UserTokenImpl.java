package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.mapper.app.generator.UserTokenCustomMapper;
import com.jokerdata.service.app.UserTokenService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class UserTokenImpl extends ServiceImpl<UserTokenCustomMapper, UserToken> implements UserTokenService {
    @Resource
    UserTokenCustomMapper targetMapper;

    @Override
    public MyPage<UserToken> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
