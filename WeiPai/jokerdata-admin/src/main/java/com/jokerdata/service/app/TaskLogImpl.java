package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.TaskLog;
import com.jokerdata.mapper.app.generator.TaskLogMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
@Service
public class TaskLogImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {

}
