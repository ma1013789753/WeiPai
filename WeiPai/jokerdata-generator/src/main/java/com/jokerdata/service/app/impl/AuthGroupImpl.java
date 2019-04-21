package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.AuthGroup;
import com.jokerdata.mapper.app.generator.AuthGroupMapper;
import com.jokerdata.service.app.AuthGroupService;
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
 * @since 2019-04-20
 */
@Service
public class AuthGroupImpl extends ServiceImpl<AuthGroupMapper, AuthGroup> implements AuthGroupService {
    @Resource
    AuthGroupMapper targetMapper;

    @Override
    public MyPage<AuthGroup> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
