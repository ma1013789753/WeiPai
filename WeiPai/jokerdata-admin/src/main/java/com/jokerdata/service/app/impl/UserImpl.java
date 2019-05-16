package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.mapper.app.generator.UserMapper;
import com.jokerdata.service.app.UserService;
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
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper targetMapper;

    /**
     * 根据手机号获取User
     * @param mobile
     * @return
     */
    @Override
    public User getUserByMobile(String mobile) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_mobile",mobile);
        return getOne(userQueryWrapper);
    }
}
