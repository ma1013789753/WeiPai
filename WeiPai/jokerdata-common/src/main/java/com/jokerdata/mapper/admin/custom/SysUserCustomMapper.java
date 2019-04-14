package com.jokerdata.mapper.admin.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.custom.SysUserCustom;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [权限管理] 用户表 Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysUserCustomMapper extends BaseMapper<SysUser> {

    SysUserCustom selectByUserName(String admin);

    MyPage<SysUserCustom> selectPageVo(@Param("page") MyPage<SysUser> myPage);

    SysUserCustom selectUserById(String uid);
}
