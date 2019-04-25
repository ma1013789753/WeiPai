package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ClassUtil;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

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


    @Autowired
    CmsCateService cmsCateService;


    @GetMapping(value = "/index",produces = "application/json;charset=UTF-8")
    public ApiResult index() {
        String type = "index";
        //广告
        IPage<Ad> adIPage = new Page<>();
        adIPage = adService.page(adIPage,new QueryWrapper<Ad>().eq("ad_type",type).eq("ad_state",0)
            .orderByAsc("ad_sort")
        );
        List<Map<String,Object>> adlist = ClassUtil.toLowBeanList(adIPage.getRecords());
        adlist.forEach(stringObjectMap -> {
            stringObjectMap.put("ad_img",stringObjectMap.get("ad_img").toString().replace("./",ClassUtil.URL));
        });
        //money
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(0);
        sharePage.setSize(10);
        sharePage =  shareService.moneyList(sharePage,true);
        sharePage.getRecords().forEach(monetListVo -> {
            monetListVo.setAdd_time_text(ClassUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent(Base64.getDecoder().decode(monetListVo.getShareContent()).toString());
            monetListVo.setUser_avatr(ClassUtil.getAvatar(monetListVo.getUserId()+""));
        });

        List<Map<String,Object>> monList = ClassUtil.toLowBeanList(sharePage.getRecords());
        monList.forEach(stringObjectMap -> {
            if(StringUtils.isEmpty(stringObjectMap.get("background_image").toString())){
                stringObjectMap.put("background_image",ClassUtil.URL+"Upload/money_push.jpg");
            }
            if(stringObjectMap.get("share_video")!=null){
                stringObjectMap.put("share_video", JSON.parseObject(stringObjectMap.get("share_video").toString()));
            }
        });

        //tuijian
        IPage<MonetListVo> recPage = new Page<>();
        recPage.setCurrent(0);
        recPage.setSize(10);
        recPage = shareService.tuiJianList(recPage,true);
        recPage.getRecords().forEach(monetListVo -> {
            monetListVo.setAdd_time_text(ClassUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent(Base64.getDecoder().decode(monetListVo.getShareContent()).toString());
            monetListVo.setUser_avatr(ClassUtil.getAvatar(monetListVo.getUserId()+""));
        });

        List<Map<String,Object>> tuiList = ClassUtil.toLowBeanList(recPage.getRecords());
        tuiList.forEach(stringObjectMap -> {
            if(StringUtils.isEmpty(stringObjectMap.get("background_image").toString())){
                stringObjectMap.put("background_image",ClassUtil.URL+"Upload/money_push.jpg");
            }
            if(stringObjectMap.get("share_video")!=null){
                stringObjectMap.put("share_video", JSON.parseObject(stringObjectMap.get("share_video").toString()));
            }
        });

        IPage<Cms> param = new Page<>();
        param.setCurrent(1);
        param.setSize(10);
        param = cmsService.page(param,new QueryWrapper<Cms>().orderByDesc("cms_sort","add_time"));
        List<Map<String,Object>> cmsList = ClassUtil.toLowBeanList(param.getRecords());
        cmsList.forEach(stringObjectMap -> {
            stringObjectMap.put("add_time_text",ClassUtil.getTaxt(stringObjectMap.get("add_time").toString()));
            stringObjectMap.put("cms_image",stringObjectMap.get("cms_image").toString().replace("./", ClassUtil.URL));
        });

        //最近收益
        List<ShareIndexVo> shareIndexVos = shareLogService.getshareInfo();
        if(shareIndexVos==null || shareIndexVos.size()<=0){
            shareIndexVos = shareLogService.getshareInfoLimit();
        }
        shareIndexVos.forEach(shareIndexVo -> {
            shareIndexVo.setTime_taxt(ClassUtil.getTaxtLater(shareIndexVo.getCheck_time()));
            shareIndexVo.setUser_avatar(ClassUtil.getAvatar(shareIndexVo.getUser_id()+""));
        });

        Map<String,Object> map = new HashMap<>();
        map.put("ad_more",adIPage.getPages()>1?true:false);
        map.put("ad_list",adlist);
        map.put("money_more",sharePage.getPages()>1?true:false);
        map.put("money_list",monList);
        map.put("rec_more",recPage.getPages()>1?true:false);
        map.put("rec_list",tuiList);
        map.put("cms_more",param.getPages()>1?true:false);
        map.put("cms_list",cmsList);
        map.put("get_money_list",ClassUtil.toLowBeanList(shareIndexVos));

        return ApiResult.success(map);
    }

    @GetMapping(value = "/money_more",produces = "application/json;charset=UTF-8")
    public PageResule money_more() {
        //money
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(1);
        sharePage.setSize(10);
        sharePage =  shareService.moneyMore(sharePage,true);
        sharePage.getRecords().forEach(monetListVo -> {
            monetListVo.setAdd_time_text(ClassUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent(Base64.getDecoder().decode(monetListVo.getShareContent()).toString());
            monetListVo.setUser_avatr(ClassUtil.getAvatar(monetListVo.getUserId()+""));
        });

        List<Map<String,Object>> monList = ClassUtil.toLowBeanList(sharePage.getRecords());
        monList.forEach(stringObjectMap -> {
            if(StringUtils.isEmpty(stringObjectMap.get("background_image").toString())){
                stringObjectMap.put("background_image",ClassUtil.URL+"Upload/money_push.jpg");
            }
            if(stringObjectMap.get("share_video")!=null){
                stringObjectMap.put("share_video", JSON.parseObject(stringObjectMap.get("share_video").toString()));
            }
        });
        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
        data.put("list",monList);
        return  PageResule.success(data).setPage((Page) sharePage);
    }

    @GetMapping(value = "/rec_more",produces = "application/json;charset=UTF-8")
    public PageResule rec_more() {
        IPage<MonetListVo> recPage = new Page<>();
        recPage.setCurrent(1);
        recPage.setSize(10);
        recPage = shareService.tuiJianList(recPage,true);
        recPage.getRecords().forEach(monetListVo -> {
            monetListVo.setAdd_time_text(ClassUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent(Base64.getDecoder().decode(monetListVo.getShareContent()).toString());
            monetListVo.setUser_avatr(ClassUtil.getAvatar(monetListVo.getUserId()+""));
        });

        List<Map<String,Object>> tuiList = ClassUtil.toLowBeanList(recPage.getRecords());
        tuiList.forEach(stringObjectMap -> {
            if(StringUtils.isEmpty(stringObjectMap.get("background_image").toString())){
                stringObjectMap.put("background_image",ClassUtil.URL+"Upload/money_push.jpg");
            }
            if(stringObjectMap.get("share_video")!=null){
                stringObjectMap.put("share_video", JSON.parseObject(stringObjectMap.get("share_video").toString()));
            }
        });
        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
        data.put("list",tuiList);
        return  PageResule.success(data).setPage((Page) recPage);
    }

    @GetMapping(value = "/cms_more",produces = "application/json;charset=UTF-8")
    public PageResule cms_more() {
        Page<Cms> recPage = new Page<>();
        recPage.setCurrent(1);
        recPage.setSize(10);
                cmsService.page(recPage,new QueryWrapper<>());

//        List<Cms> cmsdata = cmsService.list(new QueryWrapper<Cms>().orderByDesc("cms_sort","add_time").last("limit 11"));
//        List<Map<String,Object>> cmsList = ClassUtil.toLowBeanList(cmsdata);
//        cmsList.forEach(stringObjectMap -> {
//            stringObjectMap.put("add_time_text",ClassUtil.getTaxt(stringObjectMap.get("add_time").toString()));
//            stringObjectMap.put("cms_image",stringObjectMap.get("cms_image").toString().replace("./", ClassUtil.URL));
//        });

//        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
//        data.put("list",tuiList);
        return  PageResule.success("").setPage((Page) recPage);
    }
}
