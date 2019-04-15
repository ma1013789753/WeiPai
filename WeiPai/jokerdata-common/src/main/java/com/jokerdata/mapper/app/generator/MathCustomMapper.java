package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.Math;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 奖励预备 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface MathCustomMapper extends BaseMapper<Math> {

    MyPage<Math> selectPage(@Param("param") MyPage page);
}
