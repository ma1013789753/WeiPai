package com.jokerdata.common.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
public class AuthToken {
    Logger logger = LoggerFactory.getLogger(AuthToken.class);
    @Pointcut("@annotation(com.jokerdata.common.annotation.Auth)")
    public void auth(){

    }

    @Before("auth()")
    public void Authtoken(JoinPoint joinPoint){
        Object[] objects = joinPoint.getArgs();
        Arrays.asList(objects).forEach(o -> {
            logger.info(o.toString());
        });
        logger.info("xxxxxxxxxxxxxx");
    }

}
