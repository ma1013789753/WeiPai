package com.jokerdata.service.app.impl;

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

    @Override
    public MyPage<User> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
