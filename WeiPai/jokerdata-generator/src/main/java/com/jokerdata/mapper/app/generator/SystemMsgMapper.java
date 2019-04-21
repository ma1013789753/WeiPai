package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.SystemMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 系统信息 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface SystemMsgMapper extends BaseMapper<SystemMsg> {

    MyPage<SystemMsg> selectPage(@Param("param") MyPage page);
}
