package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.mapper.app.generator.UserMapper;
import com.jokerdata.mapper.app.generator.UserTokenMapper;
import com.jokerdata.service.app.UserTokenService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
@Transactional(rollbackFor = ApiException.class)
public class UserTokenImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    @Resource
    UserTokenMapper targetMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public UserToken getUserByToken(String token) {
        QueryWrapper<UserToken> wrapper = new QueryWrapper<UserToken>().eq("user_token",token);
        return getOne(wrapper);
    }

    @Override
    public UserToken getTokenByUserId(Integer userId) {

        QueryWrapper<UserToken> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        UserToken token = getOne(wrapper);
        User user = userMapper.selectById(userId);
        if(token==null){
            token = new UserToken();
            String newToken = UUID.randomUUID().toString().replaceAll("-", "");
            token.setUserToken(newToken);
            token.setAddTime(new Date().getTime()/1000+"");
            token.setUserId(user.getUserId());
            token.setUserName(user.getUserName());
            if(!save(token)){
                throw new ApiException("保存token失败");
            }
        }
        return token;
    }
}
