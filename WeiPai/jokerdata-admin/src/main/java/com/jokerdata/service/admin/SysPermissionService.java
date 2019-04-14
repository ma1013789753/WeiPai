package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.generator.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
 * [权限管理] 权限表 服务类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysPermissionService extends IService<SysPermission> {

    MyPage<SysPermission> selectRolePage(MyPage page);
}
