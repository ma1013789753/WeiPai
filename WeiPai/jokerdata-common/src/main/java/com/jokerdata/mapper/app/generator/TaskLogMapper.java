package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.entity.app.generator.TaskLog;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.TaskVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    IPage<TaskVo> getPage(@Param("page") MyPage page);
}
