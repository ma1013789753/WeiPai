package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.CoinLog;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface CoinLogMapper extends BaseMapper<CoinLog> {

    Integer getAllCount(Integer shareId);
}
