package com.jokerdata.controller.admin;


import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.service.admin.SysDeptService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.entity.admin.generator.SysDept;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * [权限管理] 部门表 前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {

    @Autowired
    SysDeptService sysDeptService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<SysDept> userMyPage = sysDeptService.selectRolePage(page);
        return Result.success(userMyPage);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增或者更新部门",notes = "")
    public Result add(@RequestBody SysDept sysDept){
        if(sysDept.getTid()==null){
            sysDept.setCreateTime(new Date());
        }
        sysDept.setUpdateTime(new Date());
        if(sysDeptService.saveOrUpdate(sysDept)){
            return Result.success();
        }
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除部门",notes = "")
    public Result del(@RequestBody SysDept sysDept){

        if(sysDept == null ||sysDept.getTid() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        if(sysDeptService.removeById(sysDept)){
            return Result.success();
        };
        return Result.error500("保存失败");
    }

    @PostMapping(value = "/all",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取所有部门",notes = "")
    public Result all(){
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        List<SysDept> deptList = sysDeptService.list(sysDeptQueryWrapper);
        return Result.success("成功",deptList);
    }


    @Login
    @PostMapping(value = "/getId",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id获取部门",notes = "")
    public Result getId(@RequestBody String rid){

        if(StringUtils.isEmpty(rid)){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }

        SysDept sysDept = sysDeptService.getById(rid);
        if(sysDept == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }

        return Result.success(sysDept);
    }
}
