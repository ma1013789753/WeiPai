package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.mapper.app.generator.UserAccountMapper;
import com.jokerdata.service.app.UserAccountService;
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
public class UserAccountImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {
    @Resource
    UserAccountMapper targetMapper;

    @Override
    public MyPage<UserAccount> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
