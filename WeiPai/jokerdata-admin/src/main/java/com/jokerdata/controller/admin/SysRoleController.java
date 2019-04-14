package com.jokerdata.controller.admin;


import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.service.admin.SysPermissionService;
import com.jokerdata.service.admin.SysRolePermissionService;
import com.jokerdata.service.admin.SysRoleService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.entity.admin.custom.SysRoleCustom;
import com.jokerdata.entity.admin.custom.SysRoleParam;
import com.jokerdata.entity.admin.generator.SysPermission;
import com.jokerdata.entity.admin.generator.SysRole;
import com.jokerdata.entity.admin.generator.SysRolePermission;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * [权限管理] 角色表 前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@RestController
@RequestMapping("/sysRole")
@Api(value = "SysUserController",description  = "角色管理")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysPermissionService sysPermissionService;

    @Autowired
    SysRolePermissionService sysRolePermissionService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<SysRoleCustom> userMyPage = sysRoleService.selectRolePage(page);
        return Result.success(userMyPage);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增或者更新角色",notes = "")
    public Result add(@RequestBody SysRole sysRole){
        sysRole.setState(1);
        if(sysRole.getRid()==null){
            sysRole.setCreateTime(new Date());
        }
        sysRole.setUpdateTime(new Date());
        if(sysRoleService.saveOrUpdate(sysRole)){
            return Result.success();
        }
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除角色",notes = "")
    public Result del(@RequestBody SysRole sysRole){

        if(sysRole == null ||sysRole.getRid() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        if(sysRoleService.removeById(sysRole)){
            return Result.success();
        };
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/getAll",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取所有角色",notes = "")
    public Result getAll(){

        List<SysRole> roles = sysRoleService.list(new QueryWrapper<>());

        return Result.success("成功",roles);
    }

    @Login
    @PostMapping(value = "/getId",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id获取角色",notes = "")
    public Result getId(@RequestBody String rid){

        if(StringUtils.isEmpty(rid)){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }

        SysRole sysRole = sysRoleService.getById(rid);
        if(sysRole == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }


        return Result.success(sysRole);
    }



    @Login
    @PostMapping(value = "/setRole",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "设置用户权限",notes = "")
    public Result setRole(@RequestBody SysRoleParam sysRoleParam){

        if(sysRoleParam == null || sysRoleParam.getRid() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        //获取主菜单的ID
        QueryWrapper<SysPermission> sysPermissionQueryWrapper = new QueryWrapper<>();
        sysPermissionQueryWrapper.eq("type","menu");
        sysPermissionQueryWrapper.eq("father",0);
        List<SysPermission> permissions = sysPermissionService.list(sysPermissionQueryWrapper);
        permissions.forEach(sysPermission -> {
            sysRoleParam.getPids().add(sysPermission.getPid());
        });
        //直接删除所有的记录
        QueryWrapper<SysRolePermission> sysRolePerWrapper = new QueryWrapper<>();
        sysRolePerWrapper.eq("role_rid",sysRoleParam.getRid());
        sysRolePermissionService.remove(sysRolePerWrapper);
        //更新
        List<SysRolePermission> srps = new ArrayList<>();
        sysRoleParam.getPids().forEach(pid ->{
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPerPid(pid);
            sysRolePermission.setRoleRid(sysRoleParam.getRid());
            srps.add(sysRolePermission);
        });
        sysRolePermissionService.saveOrUpdateBatch(srps);

        return Result.success("成功了");
    }



}
