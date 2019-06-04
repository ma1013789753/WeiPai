package com.jokerdata.controller.admin;

import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.entity.app.generator.GzhTag;
import com.jokerdata.entity.app.generator.Sign;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.service.admin.CustomerService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Login
    @PostMapping(value = "/recharge",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "后台充值",notes = "")
    public Result recharge(@RequestBody Map<String, String> map){
        if(map.size() == 0){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        int res = customerService.rechargeForuser(map);
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();

    }

    @Login
    @PostMapping(value = "/freeze",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "冻结用户",notes = "")
    public Result freeze(@RequestBody Map<String, String> map){
        if(StringUtils.isEmpty(map.get("uid"))){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        int res = customerService.freezeUser(map.get("uid"));
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();

    }

    @Login
    @PostMapping(value = "/unfreeze",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "解冻用户",notes = "")
    public Result unfreeze(@RequestBody Map<String, String> map){
        if(StringUtils.isEmpty(map.get("uid"))){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        int res = customerService.unfreezeUser(map.get("uid"));
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();

    }

    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除用户",notes = "")
    public Result delUser(@RequestBody Map<String, String> map){
        if(StringUtils.isEmpty(map.get("uid"))){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        int res = customerService.deleteUser(map.get("uid"));
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();

    }

    @Login
    @PostMapping(value = "/sign",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取用户签到列表",notes = "")
    public Result getSign(@RequestBody MyPage page, @RequestParam int userId){
        MyPage<Sign> signPage = customerService.selectSignPage(page,userId);
        return Result.success(signPage);
    }

    @Login
    @PostMapping(value = "/getAccount",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取用户子账号列表",notes = "")
    public Result getUserAccount(@RequestParam int userId, @RequestParam int accType){
        List<UserAccount> account = customerService.getUserAccount(userId,accType);
        return Result.success(account);
    }

    @Login
    @PostMapping(value = "/ban",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "封禁用户子账号",notes = "")
    public Result ban(@RequestBody Map<String, String> map){
        if(StringUtils.isEmpty(map.get("userId"))){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        int res = customerService.ban(map);
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();

    }
    @Login
    @PostMapping(value = "/unbanned",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "解封用户子账号",notes = "")
    public Result unbanned(@RequestParam int cid){
        int res = customerService.unbanned(cid);
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();

    }

    @Login
    @PostMapping(value = "/accounts",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取相关账号",notes = "")
    public Result getAccounts(@RequestBody MyPage page,@RequestParam int accType, @RequestParam int accState){
        MyPage<UserAccount> accountPage = customerService.getAccountPage(page,accType,accState);
        return Result.success(accountPage);
    }

    @Login
    @PostMapping(value = "/delAcc",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除指定账号",notes = "")
    public Result delAccount(@RequestParam int accId){
        int res = customerService.delAccountById(accId);
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();
    }

    @Login
    @PostMapping(value = "/getAcc",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ID获取指定账号",notes = "")
    public Result getAccountById(@RequestParam int accId){
        UserAccount userAccount = customerService.getAccById(accId);
        if(userAccount == null ){
            throw new MyException("账号不存在", ConstCode.CODE_404);
        }
        return Result.success("操作成功",userAccount);
    }

    @Login
    @PostMapping(value = "/rejectAcc",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审批拒绝账号",notes = "")
    public Result rejectAccountById(@RequestBody UserAccount userAccount){
        int res =  customerService.rejectAccount(userAccount);
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();
    }

    @Login
    @PostMapping(value = "/passAcc",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审批通过账号",notes = "")
    public Result passAccountById(@RequestParam int accId){
        int res = customerService.passAccount(accId);
        if(res <= 0 ){
            throw new MyException("操作失败", ConstCode.CODE_404);
        }
        return Result.success();
    }

    @Login
    @PostMapping(value = "/tagList")
    @ApiOperation(value = "获取公众号标签列表",notes = "")
    public Result getTagList(){
        List<GzhTag> tagList = customerService.getTagList();
        if(tagList == null || tagList.size() < 0 ){
            throw new MyException("没有记录", ConstCode.CODE_404);
        }
        return Result.success("操作成功",tagList);
    }

    @Login
    @PostMapping(value = "/addTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增公众号标签",notes = "")
    public Result addTag(@RequestBody GzhTag gzhTag){
        int res =  customerService.insertTag(gzhTag);
        if(res <= 0 ){
            throw new MyException("新增失败", ConstCode.CODE_404);
        }
        return Result.success("操作成功");
    }

    @Login
    @PostMapping(value = "/updateTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新公众号标签",notes = "")
    public Result updateTag(@RequestBody GzhTag gzhTag){
        int res =  customerService.updateTag(gzhTag);
        if(res <= 0 ){
            throw new MyException("更新失败", ConstCode.CODE_404);
        }
        return Result.success("操作成功");
    }

    @Login
    @PostMapping(value = "/delTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除公众号标签",notes = "")
    public Result delTag(@RequestParam int tid ){
        int res =  customerService.deleteTag(tid);
        if(res <= 0 ){
            throw new MyException("删除失败", ConstCode.CODE_404);
        }
        return Result.success("操作成功");
    }


}
