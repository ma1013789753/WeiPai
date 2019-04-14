package com.jokerdata.mapper.admin.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.generator.SysPermission;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [权限管理] 权限表 Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2018-11-26
 */
public interface SysPermissionCustomMapper extends BaseMapper<SysPermission> {

    MyPage<SysPermission> selectPageVo(@Param("page") MyPage<SysPermission> page);

}
