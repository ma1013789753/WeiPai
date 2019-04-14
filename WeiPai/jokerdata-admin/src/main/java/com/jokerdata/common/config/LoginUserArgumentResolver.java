package com.jokerdata.common.config;

import com.jokerdata.common.annotation.LoginUser;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.common.utils.Constants;
import com.jokerdata.mapper.admin.custom.SysUserCustomMapper;
import com.jokerdata.service.admin.TokenService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.mapper.admin.generator.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/** * @author sukaiyi */
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver
{
    @Resource
    private SysUserCustomMapper sysUserCustomMapper;

    @Autowired
    private TokenService tokenService;



    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是SysUser并且有CurrentUser注解则支持
         return methodParameter.getParameterType().isAssignableFrom(SysUser.class)
                 && methodParameter.hasParameterAnnotation(LoginUser.class);
    }
     @Override
     public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory){
        //取出AuthorizationInterceptor中注入的userId
          String currentUserId = (String) request.getAttribute(Constants.USERID
                  , RequestAttributes.SCOPE_REQUEST);
          if (!StringUtils.isEmpty(currentUserId)) {

              SysUser sysUser = tokenService.getRedisUser(currentUserId);
              if(sysUser != null){
                  return sysUser;
              }else{
                  QueryWrapper queryWrapper = new QueryWrapper();
                  queryWrapper.eq("uid",currentUserId);
                  sysUser = sysUserCustomMapper.selectOne(queryWrapper);
                  return sysUser;
              }
          }
          throw new MyException("当前用户获取异常", ConstCode.CODE_500);
    }
}

