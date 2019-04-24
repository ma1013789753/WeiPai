package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.SystemmsgUser;
import com.jokerdata.mapper.app.generator.SystemmsgUserMapper;
import com.jokerdata.service.app.SystemmsgUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 系统消息用户 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class SystemmsgUserImpl extends ServiceImpl<SystemmsgUserMapper, SystemmsgUser> implements SystemmsgUserService {
    @Resource
    SystemmsgUserMapper targetMapper;

    @Override
    public MyPage<SystemmsgUser> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
