package com.jokerdata.entity.admin.custom;

import com.jokerdata.entity.admin.generator.SysDept;
import com.jokerdata.entity.admin.generator.SysRole;
import com.jokerdata.entity.admin.generator.SysUser;

public class SysUserCustom extends SysUser {


    private SysRole role;

    private SysDept dept;

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }
}