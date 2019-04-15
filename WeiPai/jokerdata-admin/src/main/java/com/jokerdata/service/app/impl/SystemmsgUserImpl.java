package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.SystemmsgUser;
import com.jokerdata.mapper.app.generator.SystemmsgUserCustomMapper;
import com.jokerdata.service.app.SystemmsgUserService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 系统消息用户 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class SystemmsgUserImpl extends ServiceImpl<SystemmsgUserCustomMapper, SystemmsgUser> implements SystemmsgUserService {
    @Resource
    SystemmsgUserCustomMapper targetMapper;

    @Override
    public MyPage<SystemmsgUser> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
