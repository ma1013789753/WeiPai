package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.ShareLog;

import java.util.List;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface ShareLogMapper extends BaseMapper<ShareLog> {
    List<Integer> selectForwardReport();
}
