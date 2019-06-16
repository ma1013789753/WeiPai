package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> selectListByAccountId(@Param("ids") List<String> ids);
}
