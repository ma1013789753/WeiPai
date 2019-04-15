package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.ArticleCate;
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
public interface ArticleCateCustomMapper extends BaseMapper<ArticleCate> {

    MyPage<ArticleCate> selectPage(@Param("param") MyPage page);
}
