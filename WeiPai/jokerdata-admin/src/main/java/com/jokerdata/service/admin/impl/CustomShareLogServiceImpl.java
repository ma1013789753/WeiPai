package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.mapper.app.generator.ShareLogMapper;
import com.jokerdata.service.admin.CustomShareLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  转发服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
@Service
public class CustomShareLogServiceImpl extends ServiceImpl<ShareLogMapper, ShareLog> implements CustomShareLogService {

    @Resource
    private ShareLogMapper shareLogMapper;

    @Override
    public List<Integer> getForwardReport() {
        return shareLogMapper.selectForwardReport();
    }
}
