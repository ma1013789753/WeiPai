package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.GzhTag;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 公众号标签 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface GzhTagCustomMapper extends BaseMapper<GzhTag> {

    MyPage<GzhTag> selectPage(@Param("param") MyPage page);
}
