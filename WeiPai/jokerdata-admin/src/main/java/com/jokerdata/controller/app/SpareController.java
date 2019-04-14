package com.jokerdata.controller.app;

import java.util.Date;
import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.app.generator.Spare;
import com.jokerdata.service.app.SpareService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/spare")
public class SpareController {

    @Autowired
    private SpareService targetService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<Spare> spareMyPage = targetService.selectPage(page);
        return Result.success(spareMyPage);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增或者更新",notes = "")
    public Result add(@RequestBody Spare param){
        param.setDel(0);
        if(param.getId()==null){
            param.setCreateTime(new Date());
        }
        param.setUpdateTime(new Date());
        if(targetService.saveOrUpdate(param)){
            return Result.success();
        }
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除",notes = "")
    public Result del(@RequestBody Spare param){

        if(param == null ||param.getId() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        if(targetService.removeById(param)){
            return Result.success();
        };
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/getId",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id获取",notes = "")
    public Result getId(@RequestBody String id){

        if(StringUtils.isEmpty(id)){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }

        Spare entity = targetService.getById(id);
        if(entity == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }


        return Result.success(entity);
    }

}
