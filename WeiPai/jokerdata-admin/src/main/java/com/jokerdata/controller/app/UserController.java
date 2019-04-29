package com.jokerdata.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.ClassUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.annotations.Instantiator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GzhTagService gzhTagService;

    @Autowired
    SignService signService;

    @Autowired
    CoinService coinService;

    @Autowired
    CoinLogService coinLogService;

    @GetMapping(value = "/weibo_list",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult weibo_list(String key){
        User user = RequestHolder.getUser();
        List<Map<String,Object>> data = userAccountService.listMaps(new QueryWrapper<UserAccount>()
                            .eq("user_id",user.getUserId())
        );
        List<Map<String,Object>> dataA = new ArrayList<>();
        List<Map<String,Object>> dataB = new ArrayList<>();
        List<Map<String,Object>> dataC = new ArrayList<>();
        data.forEach(userAccount -> {
            if(ClassUtil.isExit(userAccount.get("account_limit"))){
                userAccount.put("time_text", ClassUtil.getTaxt(userAccount.get("account_limit").toString()));
            }
            if(ClassUtil.isExit(userAccount.get("num_screen"))){
                userAccount.put("num_screen",ClassUtil.URL+userAccount.get("num_screen").toString());
            }
            if(ClassUtil.isExit(userAccount.get("wechat_pic"))){
                userAccount.put("wechat_pic",ClassUtil.URL+userAccount.get("wechat_pic").toString());
            }
            if("1".equals(userAccount.get("acc_type"))){
                userAccount.put("account_avatar",ClassUtil.URL+userAccount.get("account_avatar").toString());
                userAccount.put("avatar_hd",ClassUtil.URL+userAccount.get("avatar_hd").toString());
            }else if("2".equals(userAccount.get("acc_type"))){
                userAccount.put("account_name",ClassUtil.Base64Decode(userAccount.get("account_name").toString()));

            }

            data.forEach(stringObjectMap -> {
                String accType = userAccount.get("acc_type").toString();
                switch (accType){
                    case "0":
                        dataA.add(stringObjectMap);
                        break;
                    case "1":
                        dataB.add(stringObjectMap);
                        break;
                    case "2":
                        dataC.add(stringObjectMap);
                        break;

                }
            });
            if(dataB.size()>0){

                List<Map<String,Object>> tag = gzhTagService.listMaps(new QueryWrapper<GzhTag>()
                    .select("tag_id","tag_name").orderByDesc("tag_sort")
                );
                dataB.forEach(stringObjectMap -> {
                    stringObjectMap.put("tag_name",tag.get(Integer.parseInt(stringObjectMap.get("gzh_tag_id").toString()))!=null?tag.get(Integer.parseInt(stringObjectMap.get("gzh_tag_id").toString())):"");
                });

            }

        });
        Map<String,Object> result = new HashMap<>();
        result.put("weibo_list",dataA);
        result.put("public_list",dataB);
        result.put("wechat_list",dataC);


        return ApiResult.success(result);
    }

    @GetMapping(value = "/band_weibo",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult band_weibo(String key){

        return ApiResult.error("功能未实现");
    }

    @GetMapping(value = "/weibo_news_list",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult weibo_news_list(String key){

        return ApiResult.error("功能未实现");
    }

    @GetMapping(value = "/add_weibo_t",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult add_weibo_t(String key){

        return ApiResult.error("功能未实现");
    }

    @GetMapping(value = "/user_info",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult user_info(String key){
        User user = RequestHolder.getUser();
        Map<String,Object> userInfo = ClassUtil.beanToMap(user);
        userInfo.put("user_avatar",ClassUtil.getAvatar(user.getUserId()+""));

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Map<String,Object> sign = signService.getMap(
                new QueryWrapper<Sign>().eq("user_id",user.getUserId())
                .eq("sign_date",date)
                .select("sign_id"));

        Map<String,Object> coinNum = coinLogService.getMap(
                new QueryWrapper<CoinLog>().in("log_type","task_income","sign")
                .between("add_time",new Date().getTime()/1000-24*3600,new Date().getTime()/1000)
                .select("sum(log_av_coin)")
        );
        double num = 0;
        if(coinNum.get("sum(log_av_coin)")!=null){
            num = ((BigDecimal) coinNum.get("sum(log_av_coin)")).doubleValue();
        }
        Map<String,Object> data = new HashMap<>();
        data.put("user_info",userInfo);
        data.put("is_sign",sign.get("sign_id")!=null?1:0);
        data.put("yesterday_coin",num);
        return ApiResult.success(data);
        //今日积分
    }


}
