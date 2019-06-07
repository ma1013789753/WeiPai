package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.mapper.app.generator.SystemMsgMapper;
import com.jokerdata.service.admin.CustomSystemMsgService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 系统信息 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
@Service
public class CustomSystemMsgServiceImpl extends ServiceImpl<SystemMsgMapper, SystemMsg> implements CustomSystemMsgService {

    @Resource
    private SystemMsgMapper systemMsgMapper;

    @Override
    public MyPage<SystemMsg> getMsgPage(MyPage page) {
        return systemMsgMapper.selectPageVo(page);
    }
}
