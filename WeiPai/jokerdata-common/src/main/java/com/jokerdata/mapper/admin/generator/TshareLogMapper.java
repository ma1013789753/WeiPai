package com.jokerdata.mapper.admin.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.generator.TshareLog;

import java.util.List;

/**
 * <p>
 * 互推分享表 Mapper 接口
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
public interface TshareLogMapper extends BaseMapper<TshareLog> {
        List<String> getUrlById(String shareId);
}
