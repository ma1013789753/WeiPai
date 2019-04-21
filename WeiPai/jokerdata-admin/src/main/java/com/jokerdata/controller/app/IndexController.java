package com.jokerdata.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ClassUtil;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.service.app.AdService;
import com.jokerdata.service.app.CmsService;
import com.jokerdata.service.app.ShareLogService;
import com.jokerdata.service.app.ShareService;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Index")
public class IndexController {

    @Autowired
    AdService adService;

    @Autowired
    ShareService shareService;

    @Autowired
    ShareLogService shareLogService;

    @Autowired
    CmsService cmsService;


    @GetMapping(value = "/index",produces = "application/json;charset=UTF-8")
    public ApiResult index() {
        String type = "index";

        IPage<Ad> adIPage = new Page<>();
        adIPage.setCurrent(0);
        adIPage.setSize(10);
        adIPage = adService.page(adIPage,new QueryWrapper<Ad>().eq("ad_type",type));

        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(0);
        sharePage.setSize(10);
        sharePage =  shareService.moneyList(sharePage,true);


        IPage<MonetListVo> recPage = new Page<>();
        recPage.setCurrent(0);
        recPage.setSize(10);
        recPage = shareService.tuiJianList(recPage,true);

        IPage<Cms> cmsPage = new Page<>();
        cmsPage.setCurrent(0);
        cmsPage.setSize(10);
//        cmsPage = cmsService.page(cmsPage,new QueryWrapper<Cms>().eq("cms_author","admin"));
        cmsPage = cmsService.page(cmsPage,new QueryWrapper<Cms>().eq("cms_author","admin"));

        //最近收益
        List<ShareIndexVo> shareIndexVos = shareLogService.getshareInfo();
        if(shareIndexVos==null || shareIndexVos.size()<=0){
            shareIndexVos = shareLogService.getshareInfoLimit();
        }

        shareIndexVos.forEach(shareIndexVo -> {
            Long time = Long.parseLong(shareIndexVo.getCheck_time())*1000;
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            shareIndexVo.setTime_taxt(sdf.format(date));

            shareIndexVo.setUser_avatar(ClassUtil.getAvatar(shareIndexVo.getUser_id()+""));
        });

        Map<String,Object> map = new HashMap<>();
        map.put("ad_more",adIPage.getPages()>1?true:false);
        map.put("ad_list",ClassUtil.toLowBeanList(adIPage.getRecords()));
        map.put("money_more",sharePage.getPages()>1?true:false);
        map.put("money_list",ClassUtil.toLowBeanList(sharePage.getRecords()));
        map.put("rec_more",recPage.getPages()>1?true:false);
        map.put("rec_list",ClassUtil.toLowBeanList(recPage.getRecords()));
        map.put("cms_more",cmsPage.getPages()>1?true:false);
        map.put("cms_list",ClassUtil.toLowBeanList(cmsPage.getRecords()));
        map.put("get_money_list",ClassUtil.toLowBeanList(shareIndexVos));

        return ApiResult.success(map);
    }
}
