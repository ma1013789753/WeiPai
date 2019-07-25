package com.jokerdata.common.config;

import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Map<String,String[]> params = request.getParameterMap();
        if(!StringUtils.isEmpty(request.getParameter("systemType"))){
            String key = params.get("systemType")[0];
            if(!StringUtils.isEmpty(key)&&"android".equals(key)){
                String version = params.get("appVersion")[0];
                if(compare2("0.1.63",version)>0){
                    throw new ApiException("请登录youdianshare.com下载最新版本后继续使用");
                }
            }
            if(!StringUtils.isEmpty(key)&&"ios".equals(key)){
                String version = params.get("appVersion")[0];
                if(compare2("0.1.61",version)>0){
                    throw new ApiException("请登录weipro.net下载最新版本后继续使用");
                }
            }
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