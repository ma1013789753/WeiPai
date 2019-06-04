package com.jokerdata.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.TaskLog;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.TaskVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
public interface TaskLogService extends IService<TaskLog> {

    IPage<TaskVo> getPage(MyPage page);
}
