package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.Transmit;
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
public interface TransmitCustomMapper extends BaseMapper<Transmit> {

    MyPage<Transmit> selectPage(@Param("param") MyPage page);
}
