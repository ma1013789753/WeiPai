package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.mapper.app.generator.UserTokenMapper;
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
 * @since 2019-04-20
 */
@Service
public class UserTokenImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    @Resource
    UserTokenMapper targetMapper;


    @Override
    public UserToken getUserByToken(String token) {
        QueryWrapper<UserToken> wrapper = new QueryWrapper<UserToken>().eq("user_token",token);
        return getOne(wrapper);
    }
}
