package com.jokerdata.mapper.app.custom;

import com.jokerdata.entity.app.generator.Spare;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2019-01-04
 */
public interface SpareCustomMapper extends BaseMapper<Spare> {

    MyPage<Spare> selectPage(@Param("param") MyPage page);
}
