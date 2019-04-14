package com.jokerdata.controller.admin;


import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.annotation.LoginUser;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.service.admin.SysUserService;
import com.alibaba.druid.util.StringUtils;
import com.jokerdata.entity.admin.custom.SysUserCustom;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import com.jokerdata.vo.SysUserVo;
import io.swagger.annotations.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * [权限管理] 用户表 前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@RestController
@RequestMapping("/sysUser")
@Api(value = "SysUserController",description  = "系统用户基础信息")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;


    @ApiOperation(value = "通过用户名获取用户",notes = "")
    @GetMapping("/findByUsername")
    public SysUserCustom findByUsername(@RequestParam String username,@LoginUser SysUser user) {
        return sysUserService.findByUsername(user.getUsername());
    }


    @Login
    @ApiOperation(value = "通过用户名获取用户权限",notes = "")
    @GetMapping("/info")
    public Result<SysUserVo> info(@LoginUser SysUser user) {
        SysUserVo userInfo = sysUserService.findUserInfo(user.getUsername());
        return Result.success(userInfo);
    }


    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<SysUserCustom> userMyPage = sysUserService.selectRolePage(page);
        return Result.success(userMyPage);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增或者更新用户",notes = "")
    public Result add(@RequestBody SysUser sysUser){
        sysUser.setState(1);
        //新增
        if(sysUser.getUid()==null){
            if(sysUserService.findByUsername(sysUser.getUsername())!=null){
                return Result.error500("用户已存在");
            }
            sysUser.setCreateTime(new Date());
            sysUser.setPassword(DigestUtils.sha256Hex(sysUser.getPassword()));
        }
        sysUser.setUpdateTime(new Date());
        if(!sysUserService.saveOrUpdate(sysUser)){
            return Result.error500("保存失败");
        }

        return Result.success();

    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.sha256Hex("c78110"));
    }

    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除用户",notes = "")
    public Result del(@RequestBody SysUser sysUser){

        if(sysUser == null ||sysUser.getUid() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        if(sysUserService.removeById(sysUser)){
            return Result.success();
        };
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/getId",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id获取用户",notes = "")
    public Result getId(@RequestBody String uid){

        if(StringUtils.isEmpty(uid)){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }

        SysUser sysUser = sysUserService.getById(uid);
        if(sysUser == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }

        return Result.success(sysUser);
    }

    @Login
    @PostMapping(value = "/reset",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "重置密码",notes = "")
    public Result reset(@RequestBody Map<String,String> map, @LoginUser SysUser user){

        if("".equals(map.get("newPassword"))||"".equals(map.get("checkPassword"))){
            return Result.error401("密码为空");
        }
        if(!map.get("checkPassword").equals(map.get("newPassword"))){
            return Result.error401("两次密码输入不一致");
        }

        user.setPassword(DigestUtils.sha256Hex(map.get("newPassword")));
        if(sysUserService.updateById(user)){
            return Result.success();

        }else{
            return Result.error500("更新失败");
        }
    }

}
