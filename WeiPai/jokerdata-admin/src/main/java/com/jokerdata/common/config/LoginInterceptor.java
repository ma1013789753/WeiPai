package com.jokerdata.common.config;

import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.utils.Constants;
import com.jokerdata.service.admin.TokenService;
import com.alibaba.fastjson.JSONObject;
import com.jokerdata.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(Login.class) == null) {
            return true;
        }

        String authorization = request.getHeader(Constants.AUTHORIZATION);
//        String token = tokenService.getToken(authorization);
        if (tokenService.checkToken(authorization)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.USERID, tokenService.getUserId(authorization));
            return true;
        } else {
            //会跨域？？？？？？？
            String ip = request.getRemoteAddr();
            int port = request.getRemotePort();
            String host = request.getRemoteHost();
            responseOutWithJson(response,Result.error401("host-->"+host+"ip-->"+ip+":"+port));
//            responseOutWithJson(response,Result.error401("token认证失败，请重新登录"));
//            throw new MyException("token认证失败，请重新登录", ConstCode.CODE_401);
            return false;
        }
    }


    /**
     * 以JSON格式输出
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        response.setHeader("Access-Control-Allow-Origin","*");
        String result = JSONObject.toJSONString(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}