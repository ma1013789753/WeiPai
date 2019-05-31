package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.Task;
import com.jokerdata.vo.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
public interface TaskMapper extends BaseMapper<Task> {

    List<TaskVo> getListByUser(@Param("userId")Integer userId,@Param("type") int type);

    TaskVo getTaskById(String id);
}
