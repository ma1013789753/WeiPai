package com.jokerdata.mapper.admin.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [前台用户] 用户表 Mapper 接口
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
public interface CustomerMapper extends BaseMapper<CustomerUser> {

    MyPage<CustomerUser> selectPageVo(@Param("page") MyPage<CustomerUser> myPage);
    MyPage<CustomerUser> selectRankingPageVo(@Param("page") MyPage<CustomerUser> myPage);

}
