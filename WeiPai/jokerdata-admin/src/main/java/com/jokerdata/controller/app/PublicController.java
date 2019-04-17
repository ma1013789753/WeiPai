package com.jokerdata.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.MD5;
import com.jokerdata.common.utils.CommonUtil;
import com.jokerdata.common.utils.RundomUtil;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.parames.LoginParames;
import com.jokerdata.parames.RegParames;
import com.jokerdata.parames.ResetPassword;
import com.jokerdata.parames.vo.UserInfo;
import com.jokerdata.service.app.SmsService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.service.app.UserTokenService;
import com.jokerdata.service.common.AliSmsService;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/Public")
public class PublicController {

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;


    @GetMapping(value = "/get_sms",produces = "application/json;charset=UTF-8")
    public ApiResult getId(int type,String mobile){

        if(mobile.length()!=11 || !CommonUtil.isMobileNO(mobile)){
            return new ApiResult().error("手机号码错误");
        }

        if(type != 1 && type != 2 && type != 3){
            return new ApiResult().error("验证类型错误");
        }

        String code = RundomUtil.randomCode();


        aliSmsService.sendSms(type,mobile,code);

        return ApiResult.success();

    }

    @GetMapping(value = "/reg",produces = "application/json;charset=UTF-8")
    public ApiResult reg(@Validated RegParames parames){
        QueryWrapper<Sms> wrapper = new QueryWrapper<>();
        wrapper.eq("sms_mobi",parames.getMobile())
                .eq("sms_content",parames.getSms())
                .eq("sms_type",1);


        Sms sms = smsService.getOne(wrapper);
        if(sms == null){
            return ApiResult.error("验证码不存在");
        }

        if((new Date().getTime()/1000-Long.parseLong(sms.getAdd_time()))>5*60){
            return ApiResult.error("验证码已过期");
        }
        User user = new User();
        user.setUser_mobile(parames.getMobile());
        user.setUser_password(MD5.MD5Encode(parames.getPassword(),"utf-8"));
        user.setUser_name("手机用户"+parames.getMobile().substring(7));
        userService.save(user);
        return ApiResult.success("注册成功");
    }

    @GetMapping(value = "/forget",produces = "application/json;charset=UTF-8")
    public ApiResult forget(@Validated ResetPassword parames) {
        if(!parames.getRepassword().equals(parames.getPassword())){
           return ApiResult.error("确认密码不正确");
        }
        QueryWrapper<Sms> wrapper = new QueryWrapper<>();
        wrapper.eq("sms_mobi",parames.getMobile())
                .eq("sms_content",parames.getSms())
                .eq("sms_type",2);
        Sms sms = smsService.getOne(wrapper);
        if(sms == null){
            return ApiResult.error("验证码不存在");
        }

        if((new Date().getTime()/1000-Long.parseLong(sms.getAdd_time()))>5*60){
            return ApiResult.error("验证码已过期");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("user_mobile",parames.getMobile()));
        if(user == null){
            return ApiResult.error("用户不存在");
        }
        user.setUser_password(MD5.MD5Encode(parames.getPassword(),"utf-8"));
        boolean flag = userService.updateById(user);
        if(flag){
            return ApiResult.success("成功");
        }
        return ApiResult.error("设置失败");
    }

    @GetMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public ApiResult login(@Validated LoginParames parames) {

        User user = (User) userService.getOne(new QueryWrapper<User>().eq("user_mobile",parames.getMobile()));
        if(!MD5.MD5Encode(parames.getPassword(),"utf-8").equals(user.getUser_password())){
            return ApiResult.error("密码错误");
        }
        if(user.getUser_state() == 1){
            return ApiResult.error("您的帐号异常,请联系管理员");
        }
        String str = "http://youdianshare.com/Upload/avatar/"+user.getUser_id()+".png";

        HttpURLConnection urlcon2 = null;
        try {
            URL url = new URL(str);
            urlcon2 = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long TotalSize=Long.parseLong(urlcon2.getHeaderField("Content-Length"));
//        if (TotalSize>0){
//            user.setUser_avatar(str);
//        }else{
//            user.setUser_avatar("http://youdianshare.com/Upload/avatar/default.png?"+RundomUtil.randomCode());
//        }
//        QueryWrapper<UserToken> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",user.getUser_id())
//                .eq("user_name",parames.getMobile());
//        user.setToken(userTokenService.getOne(wrapper).getUser_token());
//
        Map<String,UserInfo> map = new HashMap<>();
//        map.put("user_info",user);
        return ApiResult.success(map);
    }

    public static void main(String[] args) throws IOException {
        String regexp="^1[0-9]{10}$";
        System.out.println("15501699126".matches(regexp));
        System.out.println(CommonUtil.isMobileNO("15501699126"));
    }
}
