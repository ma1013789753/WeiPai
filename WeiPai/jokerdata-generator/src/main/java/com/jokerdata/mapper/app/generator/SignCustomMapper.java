package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.Sign;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface SignCustomMapper extends BaseMapper<Sign> {

    MyPage<Sign> selectPage(@Param("param") MyPage page);
}
