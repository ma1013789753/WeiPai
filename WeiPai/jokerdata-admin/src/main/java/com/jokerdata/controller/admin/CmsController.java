package com.jokerdata.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.app.generator.Ad;
import com.jokerdata.entity.app.generator.Cms;
import com.jokerdata.entity.app.generator.CmsCate;
import com.jokerdata.service.admin.CustomAdService;
import com.jokerdata.service.admin.CustomCmsCateService;
import com.jokerdata.service.admin.CustomCmsService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * <p>
 *  资讯 前端控制器
 * </p>
 *
 * @author aozhang
 * @since 2019-05-29
 */
@RestController
@RequestMapping("/cms")
public class CmsController {

    @Autowired
    private CustomCmsService customCmsService;

    @Autowired
    private CustomCmsCateService customCmsCateService;

    @Autowired
    private CustomAdService customAdService;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取资讯列表",notes = "")
    public Result getPage(@RequestBody MyPage page){
            IPage<Cms> data = customCmsService.page(page,new QueryWrapper<Cms>()
                    .excludeColumns(Cms.class,"cms_content")
                    .orderByDesc("add_time")
                    .like("cms_title",page.getSearch1())
            );
            return Result.success(data);
    }

    @Login
    @PostMapping(value = "/getAdPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取广告列表",notes = "")
    public Result getAdPage(@RequestBody MyPage page){
        IPage<Ad> data = customAdService.page(page,new QueryWrapper<Ad>()
                .orderByDesc("add_time")
        );
        return Result.success(data);
    }

    @Login
    @PostMapping(value = "/del",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除资讯",notes = "")
    public Result getPage(@RequestBody Cms cms){
        boolean res = customCmsService.removeById(cms.getCmsId());
        if(!res){
            throw new MyException("删除失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }

    @Login
    @PostMapping(value = "/getById",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取指定资讯",notes = "")
    public Result getNewsById(@RequestBody Cms cms){
        if(cms == null || cms.getCmsId() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        cms = customCmsService.getById(cms.getCmsId());
        return Result.success("操作成功",cms);
    }

    @Login
    @PostMapping(value = "/updateNews",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新资讯",notes = "")
    public Result updateNewsById(@RequestBody Cms cms){
        Boolean res = false;
        cms.setAddTime((new Date().getTime()/1000)+"");
        if(cms != null && cms.getCmsId() == null){
            res = customCmsService.save(cms);
        }else{
            res =  customCmsService.updateById(cms);
        }
        if(!res){
            throw new MyException("操作失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }

    @Login
    @PostMapping(value = "/getCategory")
    @ApiOperation(value = "获取资讯分类",notes = "")
    public Result getCategory(@RequestParam int type){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(type == 0){
            queryWrapper.eq("cate_state",0);
        }
        queryWrapper.orderByDesc("cate_sort");
        List<CmsCate> cmsCates = customCmsCateService.list(queryWrapper);
        return Result.success("操作成功",cmsCates);
    }

    @Login
    @PostMapping(value = "/updateTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新资讯标签",notes = "")
    public Result updateTag(@RequestBody CmsCate cmsCate,@RequestParam int type){
        boolean res = false;
        if(type == 0){
            res = customCmsCateService.updateById(cmsCate);
        }else if(type == 1){
            cmsCate.setCateState(true);
            res = customCmsCateService.updateById(cmsCate);
        }else{
            cmsCate.setCateState(false);
            res = customCmsCateService.updateById(cmsCate);
        }
        if(!res){
            throw new MyException("更新失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }

    @Login
    @PostMapping(value = "/addTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加资讯标签",notes = "")
    public Result addTag(@RequestBody CmsCate cmsCate){
        boolean res = customCmsCateService.save(cmsCate);
        if(!res){
            throw new MyException("操作失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }


}
