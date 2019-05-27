package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Task;
import com.jokerdata.vo.TaskVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
public interface TaskService extends IService<Task> {

    List<TaskVo> getListByUser(Integer userId, int type);
}
