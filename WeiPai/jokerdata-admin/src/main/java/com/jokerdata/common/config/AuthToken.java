package com.jokerdata.common.config;

import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.service.app.UserAccountService;
import com.jokerdata.service.app.UserService;
import com.jokerdata.service.app.UserTokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


@Component
@Aspect
public class AuthToken {
    Logger logger = LoggerFactory.getLogger(AuthToken.class);

    @Autowired
    UserService userService;

    @Autowired
    UserTokenService userTokenService;


    @Pointcut("@annotation(com.jokerdata.common.annotation.Auth)")
    public void auth(){

    }

    @Before("auth()")
    public void Authtoken(JoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
        logger.info(Arrays.toString(joinPoint.getArgs()));
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        List<String> strings = Arrays.asList(methodSignature.getParameterNames());
        Auth auth = method.getAnnotation(Auth.class);
        if(!auth.value()){
           return;
        }
        if(strings.indexOf("key")<0){
            return;
        }
        String key = joinPoint.getArgs()[strings.indexOf("key")].toString();
        if(StringUtils.isEmpty(key)){
            throw new ApiException("请登录");
        }
        UserToken userToken = userTokenService.getUserByToken(key);
        if(userToken == null){
            throw new ApiException("请登录");
        }
        User user = userService.getById(userToken.getUserId());
        if(user.getUserState().equals("1")){
            throw new ApiException("账号异常,请联系管理员");
        }
        RequestHolder.add(user);
    }

}
