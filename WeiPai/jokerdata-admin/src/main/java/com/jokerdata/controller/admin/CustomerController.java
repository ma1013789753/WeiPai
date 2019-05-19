package com.jokerdata.controller.admin;

import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.service.admin.CustomerService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [前台用户] 用户表 前端控制器
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@RestController
@RequestMapping("/customer")
@Api(value = "CustomerController",description  = "前台用户基础信息")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取前台用户列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<CustomerUser> userMyPage = customerService.selectUserPage(page);
        return Result.success(userMyPage);
    }

    @Login
    @PostMapping(value = "/ranking",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取用户分享榜单",notes = "")
    public Result getRanking(@RequestBody MyPage page){
        MyPage<CustomerUser> rankingPage = customerService.selectRankingPage(page);
        return Result.success(rankingPage);
    }

    @Login
    @PostMapping(value = "/getById",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id获取前台用户",notes = "")
    public Result getUserById(@RequestBody String uid){
        if(StringUtils.isEmpty(uid)){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        CustomerUser user = customerService.getById(uid);
        if(user == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }
        return Result.success(user);
    }

}
