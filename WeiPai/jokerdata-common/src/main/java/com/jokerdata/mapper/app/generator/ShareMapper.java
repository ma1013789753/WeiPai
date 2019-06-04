package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface ShareMapper extends BaseMapper<Share> {
    MyPage<Share> selectPageVo(@Param("page") MyPage<Share> myPage,@Param("typeId")int typeId);
    Share selectById(@Param("id")int id);
}
