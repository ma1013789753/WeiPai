package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.PdLog;
import com.jokerdata.mapper.app.generator.PdLogMapper;
import com.jokerdata.service.app.PdLogService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 预存款变更日志表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class PdLogImpl extends ServiceImpl<PdLogMapper, PdLog> implements PdLogService {
    @Resource
    PdLogMapper targetMapper;

}
