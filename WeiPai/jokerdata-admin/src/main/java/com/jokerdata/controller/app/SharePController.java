package com.jokerdata.controller.app;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.ShareIndexParams;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.UserAccept;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ShareP")
@Transactional(rollbackFor = ApiException.class)
public class SharePController {

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    ShareTagService shareTagService;

    @Autowired
    ShareService shareService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    ShareLogService shareLogService;


    @GetMapping(value = "/pub_share_index",produces = "application/json;charset=UTF-8")
    public PageResule pub_share_index(ShareIndexParams shareIndexParams) {
        UserToken userToken = userTokenService.getUserByToken(shareIndexParams.getKey());
//        if(userToken.getu!=0){
//            output_error('账号异常,请联系管理员',array('login' => '0'));
//        }
        List<ShareTag> data = shareTagService.list(new QueryWrapper<ShareTag>().orderByDesc("tag_sort"));
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(Integer.parseInt(shareIndexParams.curpage));
        sharePage.setSize(10);

        sharePage = shareService.shareList(sharePage,shareIndexParams);
        List<Map<String,Object>> monList = ShareUtil.toLowBeanList(sharePage.getRecords());
        monList.forEach(stringObjectMap -> {

            if(stringObjectMap.get("share_video")!=null){
                stringObjectMap.put("share_video", JSON.parseObject(stringObjectMap.get("share_video").toString()));
            }
            if(stringObjectMap.get("share_image")!=null && !StringUtils.isEmpty(stringObjectMap.get("share_image").toString())){
                stringObjectMap.put("share_image", JSON.parseArray(stringObjectMap.get("share_image").toString()));
            }
        });

        Map<String,Object> result = new HashMap<>();
        result.put("TagList", ShareUtil.toLowBeanList(data));
        result.put("list",monList);
        return PageResule.success(result).setPage((Page)sharePage);
    }

    @GetMapping(value = "/money_list",produces = "application/json;charset=UTF-8")
    public PageResule money_list(ShareIndexParams shareIndexParams) {
//        UserToken userToken = userTokenService.getUserByToken(shareIndexParams.getKey());
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(Integer.parseInt(shareIndexParams.curpage));
        sharePage.setSize(10);
        sharePage = shareService.shareMoneyList(sharePage,shareIndexParams);

        List<Map<String,Object>> monList = ShareUtil.toLowBeanList(sharePage.getRecords());
        monList.forEach(stringObjectMap -> {

            if(stringObjectMap.get("share_video")!=null){
                stringObjectMap.put("share_video", JSON.parseObject(stringObjectMap.get("share_video").toString()));
            }
            if(stringObjectMap.get("share_image")!=null){
                stringObjectMap.put("share_image", JSON.parseArray(stringObjectMap.get("share_image").toString()));
            }

        });
        Map<String,Object> result = new HashMap<>();
        result.put("list",monList);
        return PageResule.success(result).setPage((Page)sharePage);
    }
    @GetMapping(value = "/pub_share_list",produces = "application/json;charset=UTF-8")
    public PageResule pub_share_list(ShareIndexParams shareIndexParams) {

        return this.pub_share_index(shareIndexParams);
    }

    @GetMapping(value = "/share_info",produces = "application/json;charset=UTF-8")
    public ApiResult share_info(String user_id,String share_id) {
//        UserToken userToken = userTokenService.getUserByToken(shareIndexParams.getKey());

        Share share = shareService.getOne(new QueryWrapper<Share>().eq("share_id",share_id));
        UserAccount userAccount = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("account_id",share.getAccountId()));
        share.setUserName(userAccount.getUserName());
        Map<String,Object> data = ShareUtil.toLowBean(share);
        data.put("account_avatar", ShareUtil.getPic(userAccount.getAccountAvatar()));
        data.put("avatar_hd",  ShareUtil.getPic(ShareUtil.URL+userAccount.getAvatarHd()));
        data.put("account_name",userAccount.getAccountName());
        data.put("v_legalize",userAccount.getVLegalize());
        data.put("ratio",((double)share.getHaveSharedNum())/((double)share.getShareNum())*100+"");
        if(!StringUtils.isEmpty(share.getShareVideo())){
            //要做一个获取视频的东西
            data.put("share_video",JSON.parse(share.getShareVideo()));
        }
//        if(!StringUtils.isEmpty(share.getShareVideo())){
//            //转JSON 不需要吧
//        }
        if(!StringUtils.isEmpty(share.getShareImage())){
            data.put("share_image",JSON.parse(share.getShareImage()));
        }
        data.put("user_avatr", ShareUtil.getAvatar(userAccount.getUid()));
        if(!StringUtils.isEmpty(share.getBackgroundImage())){
            data.put("background_image", ShareUtil.URL+share.getBackgroundImage());
        }else{
            data.put("background_image", ShareUtil.URL+"/Upload/money_push.jpg");
        }
        data.put("add_time_text", ShareUtil.getTaxt(share.getAddTime()));
        data.put("share_content",ShareUtil.Base64Decode(share.getShareContent()));
        if(!StringUtils.isEmpty(share.getShareImg())){
            data.put("share_img", ShareUtil.URL+share.getShareImg());
        }
         if("2".equals(share.getShareType())){
            data.put("account_name",ShareUtil.Base64Decode(userAccount.getAccountName()));
        }
        List<Map<String, Object>> userList = shareLogService.getshareInfoUser(share.getShareId());
        data.put("user_list",userList);
        List<UserAccept> datas = userAccountService.getUserAccept(user_id,share_id);
        Map<String,Object> map = new HashMap<>();
        map.put("info",data);
        map.put("obout",datas);
        return ApiResult.success(map);
    }

    @GetMapping(value = "/user_list",produces = "application/json;charset=UTF-8")
    public PageResule user_list(Integer curpage,String share_id) {
        Share share = shareService.getOne(new QueryWrapper<Share>().eq("share_id",share_id));
        IPage<ShareLog> logPage = new Page<>();
        logPage.setCurrent(curpage);
        logPage.setSize(10);
        IPage<Map<String,Object>> userList = shareLogService.shareUserList(logPage,share_id);
        userList.getRecords().forEach(stringObjectMap -> {
            if("2".equals(stringObjectMap.get("acc_type"))){
                stringObjectMap.put("account_name", ShareUtil.Base64Decode((String) stringObjectMap.get("account_name")));
            }
            stringObjectMap.put("time_text", ShareUtil.getTaxt((String) stringObjectMap.get("add_time")));
        });
        Map<String,Object> data = new HashMap<>();
        data.put("num",share.getHaveSharedNum());
        data.put("proportion",share.getHaveSharedNum()/share.getShareNum());
        data.put("list",userList.getRecords());

        return PageResule.success(data).setPage((Page) logPage);

    }

}
