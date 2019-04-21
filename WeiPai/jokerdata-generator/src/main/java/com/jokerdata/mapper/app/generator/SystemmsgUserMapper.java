package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.SystemmsgUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 系统消息用户 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface SystemmsgUserMapper extends BaseMapper<SystemmsgUser> {

    MyPage<SystemmsgUser> selectPage(@Param("param") MyPage page);
}
