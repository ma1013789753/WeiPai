package com.jokerdata.controller.admin;


import com.jokerdata.common.annotation.Login;
import com.jokerdata.service.admin.SysPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.entity.admin.generator.SysPermission;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * [权限管理] 权限表 前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@RestController
@RequestMapping("/sysPerm")
public class SysPermissionController {

    @Autowired
    SysPermissionService sysPermissionService;

    @Login
    @PostMapping(value = "/getMenuVo",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取菜单树状图",notes = "")
    public Result getMenuVo(){

        QueryWrapper<SysPermission> sysPermissionQueryWrapper = new QueryWrapper<>();
        sysPermissionQueryWrapper.eq("type","menu");
        sysPermissionQueryWrapper.orderByAsc("father");
        List<SysPermission> permissions = sysPermissionService.list(sysPermissionQueryWrapper);

//        Set<MenuVo> menuVos = new HashSet<>();
//
//        permissions.forEach(permission -> {
//            menuVos.add(new MenuVo(permission.getPid(), permission.getFather(), permission.getIcon(), permission.getResources(), permission.getTitle(),permission.getMemo(),permission.getCreateTime()));
//        });

        return Result.success("成功", permissions);
    }


    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<SysPermission> permissionMyPage = sysPermissionService.selectRolePage(page);
        return Result.success(permissionMyPage);
    }

    @Login
    @PostMapping(value = "/edit",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "编辑菜单",notes = "")
    public Result edit(@RequestBody SysPermission sysPermission){
        sysPermissionService.saveOrUpdate(sysPermission);
        return Result.success("成功");
    }


}
