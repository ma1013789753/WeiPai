package com.jokerdata.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.ClassUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.entity.app.generator.GzhTag;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.service.app.GzhTagService;
import com.jokerdata.service.app.UserAccountService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.service.app.UserTokenService;
import com.jokerdata.vo.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.annotations.Instantiator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GzhTagService gzhTagService;

    @GetMapping(value = "/weibo_list",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult weibo_list(String key){
//        User userToken = userService.getById(user_id);
        UserToken userToken = userTokenService.getUserByToken(key);
        List<Map<String,Object>> data = userAccountService.listMaps(new QueryWrapper<UserAccount>()
                            .eq("user_id",userToken.getUserId())
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
                if("0".equals(userAccount.get("acc_type"))){
                    dataA.add(stringObjectMap);
                }
                if("1".equals(userAccount.get("acc_type"))){
                    dataB.add(stringObjectMap);
                }
                if("2".equals(userAccount.get("acc_type"))){
                    dataC.add(stringObjectMap);
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

}
