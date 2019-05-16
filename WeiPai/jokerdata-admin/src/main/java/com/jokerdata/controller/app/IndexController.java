package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/Index")
@Transactional(rollbackFor = ApiException.class)
public class IndexController {

    @Autowired
    AdService adService;

    @Autowired
    ShareService shareService;

    @Autowired
    ShareLogService shareLogService;

    @Autowired
    CmsService cmsService;


    @Autowired
    CmsCateService cmsCateService;

    /**
     * 首页数据
     * @return
     */
    @GetMapping(value = "/index",produces = "application/json;charset=UTF-8")
    @ApiOperation(value="首页数据", notes="首页数据")
    public ApiResult index() {
        String type = "index";
        //广告
        IPage<Ad> adIPage = new Page<>();
        adIPage.setCurrent(1);
        adIPage.setSize(10);
        adIPage = adService.page(adIPage,new QueryWrapper<Ad>()
                .eq("ad_type",type)
                .eq("ad_state",0)
                .orderByAsc("ad_sort")
        );
        List<Map<String,Object>> adlist = ShareUtil.toLowBeanList(adIPage.getRecords());
        adlist.forEach(map -> {
            map.put("ad_img",ShareUtil.getPic(map.get("ad_img").toString()));
            map.put("add_time_text",ShareUtil.getTaxt(map.get("add_time")));
        });

        //money
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(1);
        sharePage.setSize(10);
        sharePage =  shareService.moneyList(sharePage,true);

        //tuijian
        IPage<MonetListVo> recPage = new Page<>();
        recPage.setCurrent(0);
        recPage.setSize(10);
        recPage = shareService.tuiJianList(recPage,true);;


        IPage<Cms> param = new Page<>();
        param.setCurrent(1);
        param.setSize(10);
        param = cmsService.page(param,new QueryWrapper<Cms>().orderByDesc("cms_sort")
                                    .orderByDesc("add_time")
        );
        List<Map<String,Object>> cmsList = ShareUtil.toLowBeanList(param.getRecords());
        cmsList.forEach(stringObjectMap -> {
            stringObjectMap.put("add_time_text", ShareUtil.getTaxt(stringObjectMap.get("add_time").toString()));
            stringObjectMap.put("cms_image",ShareUtil.getPic(stringObjectMap.get("cms_image")));
        });

        //最近收益
        List<ShareIndexVo> shareIndexVos = shareLogService.getshareInfo();
        if(shareIndexVos==null || shareIndexVos.size()<=0){
            shareIndexVos = shareLogService.getshareInfoLimit();
        }
        shareIndexVos.forEach(shareIndexVo -> {
            shareIndexVo.setTime_taxt(ShareUtil.getTaxtLater(shareIndexVo.getCheck_time()));
            shareIndexVo.setUser_avatar(ShareUtil.getAvatar(shareIndexVo.getUser_id()+""));
        });
        Map<String,Object> map = new HashMap<>();
        map.put("ad_more",adIPage.getPages()>1?true:false);
        map.put("ad_list",adlist);
        map.put("money_more",sharePage.getPages()>1?true:false);
        map.put("money_list",ShareUtil.toLowBeanList(sharePage.getRecords()));
        map.put("rec_more",recPage.getPages()>1?true:false);
        map.put("rec_list",ShareUtil.toLowBeanList(recPage.getRecords()));
        map.put("cms_more",param.getPages()>1?true:false);
        map.put("cms_list",cmsList);
        map.put("get_money_list", ShareUtil.toLowBeanList(shareIndexVos));
        return ApiResult.success(map);
    }

    @ApiOperation(value="更多记录", notes="更多记录")
    @GetMapping(value = "/money_more",produces = "application/json;charset=UTF-8")
    public PageResule money_more(int curpage) {
        //money
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(curpage);
        sharePage.setSize(10);
        sharePage =  shareService.moneyMore(sharePage,true);
        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
        data.put("list",ShareUtil.toLowBeanList(sharePage.getRecords()));
        return  PageResule.success(data).setPage((Page) sharePage);
    }

    @ApiOperation(value="更多记录", notes="更多记录")
    @GetMapping(value = "/rec_more",produces = "application/json;charset=UTF-8")
    public PageResule rec_more(int curpage) {
        IPage<MonetListVo> recPage = new Page<>();
        recPage.setCurrent(curpage);
        recPage.setSize(10);
        recPage = shareService.tuiJianList(recPage,true);
        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
        data.put("list",ShareUtil.toLowBeanList(recPage.getRecords()));
        return  PageResule.success(data).setPage((Page) recPage);
    }


    @ApiOperation(value="更多记录", notes="更多记录")
    @GetMapping(value = "/cms_more",produces = "application/json;charset=UTF-8")
    public PageResule cms_more(int curpage) {
        IPage<Cms> recPage = new Page<>();
        recPage.setCurrent(curpage);
        recPage.setSize(10);
        recPage = cmsService.page(recPage,new QueryWrapper<Cms>()
                .orderByDesc("cms_sort")
                .orderByDesc("add_time"));
        List<Map<String,Object>> cmsList = ShareUtil.toLowBeanList(recPage.getRecords());
        cmsList.forEach(stringObjectMap -> {
            stringObjectMap.put("add_time_text",ShareUtil.getTaxt(stringObjectMap.get("add_time").toString()));
            stringObjectMap.put("cms_image",ShareUtil.getPic(stringObjectMap.get("cms_image")));
        });

        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
        data.put("list",cmsList);
        return  PageResule.success(data).setPage((Page) recPage);
    }

    @ApiOperation(value="cms详情", notes="cms详情")
    @GetMapping(value = "/cms_info",produces = "application/json;charset=UTF-8")
    public ApiResult cms_more(String cms_id) {
        if(StringUtils.isEmpty(cms_id)){
            return ApiResult.error("参数不合法");
        }

        Cms cms = cmsService.getById(cms_id);
        if(cms == null){
            return ApiResult.error("参数不合法");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("cms_image",ShareUtil.getPic(map.get("cms_image")));
        map.put("add_time_text",ShareUtil.getTaxt(map.get("add_time")));

        return ApiResult.success(map);
    }
}
