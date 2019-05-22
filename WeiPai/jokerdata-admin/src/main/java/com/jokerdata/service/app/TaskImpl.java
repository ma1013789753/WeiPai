package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Task;
import com.jokerdata.mapper.app.generator.TaskMapper;
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
public class TaskImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}
