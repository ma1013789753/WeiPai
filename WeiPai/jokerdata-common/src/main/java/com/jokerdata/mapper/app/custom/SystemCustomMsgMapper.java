package com.jokerdata.mapper.app.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
    * 系统信息 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface SystemCustomMsgMapper extends BaseMapper<SystemMsg> {

    MyPage<SystemMsg> selectPage(@Param("param") MyPage page);

    List<SystemMsg> getMsgList(@Param("userId")Integer userId, Page<SystemMsg> page);
}
