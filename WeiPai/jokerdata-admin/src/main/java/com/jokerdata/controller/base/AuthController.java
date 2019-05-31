package com.jokerdata.controller.base;

import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.annotation.LoginUser;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.service.admin.SysUserService;
import com.jokerdata.service.admin.TokenService;
import com.alibaba.druid.util.StringUtils;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.vo.Result;
import com.jokerdata.vo.SysUserVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/auth")
@Api(value = "AuthController",description  = "用户token认证")
public class AuthController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "获取用户token",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "JsonObject", name = "map", value = "包含username,password参数", required = true, paramType = "body"),
    })
    @PostMapping(value = "/login")
    public Result<String> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new MyException("用户名或密码不能为空", ConstCode.CODE_403);
        }
        SysUser user = sysUserService.login(username,password);
        if(user == null){
            throw new MyException("密码错误",ConstCode.CODE_403);
        }
        String token = tokenService.createToken(String.valueOf(user.getUid()));

        return Result.success("登录成功", token);
    }

    @Login
    @ApiOperation(value = "登出",notes = "")
    @PostMapping(value = "/logout")
    public Result<SysUserVo> logout(@LoginUser SysUser sysUser) {
        //清除token
        tokenService.deleteToken(String.valueOf(sysUser.getUid()));
        return Result.success();
    }


    @Login
    @ApiOperation(value = "第三方验证用户登陆",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "JsonObject", name = "map", value = "包含type,code参数", required = true, paramType = "body"),
    })
    @PostMapping(value = "/logincheck")
    public Result<String> loginCheck(@RequestBody Map<String, String> map,@LoginUser SysUser user) {
        String type = map.get("platform");
        String code = map.get("code");
        boolean result = tokenService.checkOauthToken(code, user, type == "wechat" ? 1 : 2);
        if (result) {
            return Result.success("验证通过","success");
        }
        return Result.error403("授权验证失败");
    }


}
