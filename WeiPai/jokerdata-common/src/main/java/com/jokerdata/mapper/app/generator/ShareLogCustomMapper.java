package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.ShareLog;
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
public interface ShareLogCustomMapper extends BaseMapper<ShareLog> {

    MyPage<ShareLog> selectPage(@Param("param") MyPage page);
}
