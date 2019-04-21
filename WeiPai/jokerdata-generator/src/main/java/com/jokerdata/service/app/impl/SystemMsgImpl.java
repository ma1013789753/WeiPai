package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.mapper.app.generator.SystemMsgMapper;
import com.jokerdata.service.app.SystemMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 系统信息 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class SystemMsgImpl extends ServiceImpl<SystemMsgMapper, SystemMsg> implements SystemMsgService {
    @Resource
    SystemMsgMapper targetMapper;

    @Override
    public MyPage<SystemMsg> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
