package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.custom.SysRoleCustom;
import com.jokerdata.entity.admin.generator.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
 * [权限管理] 角色表 服务类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysRoleService extends IService<SysRole> {

    MyPage<SysRoleCustom> selectRolePage(MyPage<SysRoleCustom> page);

    int insertOrUpdate(SysRole sysRole);
}
