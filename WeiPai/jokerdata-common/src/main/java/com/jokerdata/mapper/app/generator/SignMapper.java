package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.entity.app.generator.Sign;
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
public interface SignMapper extends BaseMapper<Sign> {

    MyPage<Sign> selectPageVo(@Param("page") MyPage<Sign> myPage,@Param("userId")int userId);

}
