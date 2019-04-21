package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.Config;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface ConfigMapper extends BaseMapper<Config> {

    MyPage<Config> selectPage(@Param("param") MyPage page);
}
