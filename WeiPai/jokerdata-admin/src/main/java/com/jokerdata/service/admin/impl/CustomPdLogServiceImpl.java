package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.PdLog;
import com.jokerdata.mapper.app.generator.PdLogMapper;
import com.jokerdata.service.admin.CustomPdLogService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 预存款变更日志表 服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class CustomPdLogServiceImpl extends ServiceImpl<PdLogMapper, PdLog> implements CustomPdLogService {


}
