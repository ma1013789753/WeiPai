package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.mapper.app.custom.SystemCustomMsgMapper;
import com.jokerdata.mapper.app.generator.SystemMsgMapper;
import com.jokerdata.service.app.SystemMsgService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Resource
    SystemCustomMsgMapper systemCustomMsgMapper;



    @Override
    public Page<SystemMsg> getMsgList(Integer userId, Integer curpage) {
        Page<SystemMsg> page = new Page<SystemMsg>(curpage, 10);
        List<SystemMsg> data  = systemCustomMsgMapper.getMsgList(userId,page);
        page.setRecords(data);
        return page;
    }

    @Override
    public IPage<Map<String, Object>> getPageList(IPage<Map<String, Object>> systemMsgIPage, Integer userId) {
        return systemCustomMsgMapper.getPageList(systemMsgIPage,userId);
    }

    @Override
    public IPage<Map<String, Object>> getPageListShop(IPage<Map<String, Object>> systemMsgIPage, Integer userId) {
        return systemCustomMsgMapper.getPageListShop(systemMsgIPage,userId);
    }

    @Override
    public List<Map<String, Object>> countSum(Integer userId) {
        return systemCustomMsgMapper.countSum(userId);
    }
}
