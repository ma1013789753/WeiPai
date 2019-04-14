package com.jokerdata.entity.admin.custom;

import com.jokerdata.entity.admin.generator.SysPermission;
import com.jokerdata.entity.admin.generator.SysRole;

import java.util.Set;

public class SysRoleCustom extends SysRole {

    // 权限列表
    private Set<SysPermission> permissions;


    public Set<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
    }


}