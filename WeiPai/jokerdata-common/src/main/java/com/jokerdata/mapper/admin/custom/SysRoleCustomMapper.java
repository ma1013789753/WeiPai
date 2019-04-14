package com.jokerdata.mapper.admin.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.custom.SysRoleCustom;
import com.jokerdata.entity.admin.generator.SysRole;
import com.jokerdata.vo.ParamSome;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * [权限管理] 角色表 Mapper 接口
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysRoleCustomMapper extends BaseMapper<SysRole> {

    Set<SysRoleCustom> selectByUserName(String admin);

    List<SysRoleCustom> selectPage(@Param("param") ParamSome paramSome);

}
