package com.jokerdata.mapper.admin.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.generator.SysDept;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [权限管理] 部门表 Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysDeptCustomMapper extends BaseMapper<SysDept> {

    MyPage<SysDept> selectPageVo(@Param("page") MyPage<SysDept> myPage);
}
