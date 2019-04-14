package ${package.Controller};

import java.util.Date;
import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Login;
import ${package.Entity}.${entity};
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
    * ${table.comment!} 前端控制器
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} targetService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
        MyPage<${entity}> spareMyPage = targetService.selectPage(page);
        return Result.success(spareMyPage);
    }

    @Login
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增或者更新",notes = "")
    public Result add(@RequestBody ${entity} param){
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
    public Result del(@RequestBody ${entity} param){

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

    ${entity} entity = targetService.getById(id);
        if(entity == null){
            throw new MyException("数据异常", ConstCode.CODE_404);
        }


        return Result.success(entity);
    }

}
</#if>
