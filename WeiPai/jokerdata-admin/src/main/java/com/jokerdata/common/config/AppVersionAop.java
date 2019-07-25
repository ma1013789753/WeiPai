package com.jokerdata.common.config;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.exception.ApiException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

//@Component
//@Aspect
//@Order(1)
public class AppVersionAop {
    Logger logger = LoggerFactory.getLogger(AppVersionAop.class);



    @Before("execution(* com.jokerdata.controller.app.*.*(..))")
    public void handleControllerMethod(JoinPoint joinPoint) throws Throwable {
        System.out.println("进入时间切面,执行before..");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        List<String> strings = Arrays.asList(methodSignature.getParameterNames());

        if(strings.indexOf("systemType")>0){
            String key = joinPoint.getArgs()[strings.indexOf("systemType")].toString();
            if(!StringUtils.isEmpty(key)&&"android".equals(key)){
                String version = joinPoint.getArgs()[strings.indexOf("appVersion")].toString().replace(".","0");
                if(compare2("0.1.82",version)>0){
                    throw new ApiException("请登录youdianshare.com下载最新版本后继续使用");
                }
            }
            if(!StringUtils.isEmpty(key)&&"ios".equals(key)){
                String version = joinPoint.getArgs()[strings.indexOf("appVersion")].toString().replace(".","0");
                if(compare2("0.1.82",version)>0){
                    throw new ApiException("请登录weipro.net下载最新版本后继续使用");
                }
            }
        }
    }

    public static int compare2(String v1,String v2){
        int i=0,j=0,x=0,y=0;
        int v1Len=v1.length();
        int v2Len=v2.length();
        char c;
        do {
            while(i<v1Len){//计算出V1中的点之前的数字
                c=v1.charAt(i++);
                if(c>='0' && c<='9'){
                    x=x*10+(c-'0');//c-‘0’表示两者的ASCLL差值
                }else if(c=='.'){
                    break;//结束
                }else{
                    //无效的字符
                }
            }
            while(j<v2Len){//计算出V2中的点之前的数字
                c=v2.charAt(j++);
                if(c>='0' && c<='9'){
                    y=y*10+(c-'0');
                }else if(c=='.'){
                    break;//结束
                }else{
                    //无效的字符
                }
            }
            if(x<y){
                return -1;
            }else if(x>y){
                return 1;
            }else{
                x=0;y=0;
                continue;
            }

        } while ((i<v1Len) || (j<v2Len));
        return 0;
    }



}
