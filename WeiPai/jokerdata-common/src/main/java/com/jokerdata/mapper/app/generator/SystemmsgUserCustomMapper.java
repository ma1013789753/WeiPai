package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.SystemmsgUser;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 系统消息用户 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface SystemmsgUserCustomMapper extends BaseMapper<SystemmsgUser> {

    MyPage<SystemmsgUser> selectPage(@Param("param") MyPage page);
}
