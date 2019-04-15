package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.User;
import com.jokerdata.mapper.app.generator.UserCustomMapper;
import com.jokerdata.service.app.UserService;
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
 * @since 2019-04-15
 */
@Service
public class UserImpl extends ServiceImpl<UserCustomMapper, User> implements UserService {
    @Resource
    UserCustomMapper targetMapper;

    @Override
    public MyPage<User> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
