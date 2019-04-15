package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.AuthGroupAccess;
import com.jokerdata.mapper.app.generator.AuthGroupAccessCustomMapper;
import com.jokerdata.service.app.AuthGroupAccessService;
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
public class AuthGroupAccessImpl extends ServiceImpl<AuthGroupAccessCustomMapper, AuthGroupAccess> implements AuthGroupAccessService {
    @Resource
    AuthGroupAccessCustomMapper targetMapper;

    @Override
    public MyPage<AuthGroupAccess> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
