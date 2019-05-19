package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.generator.SysUser;

public interface TokenService {

    String createToken(String userId);

    void deleteToken(String userId);

    String getUserId(String token);

    boolean checkToken(String token);

    SysUser getRedisUser(String userId);

    //获取第三方接口token
    String getOauthToken(String code);

    //返回第三方授权结果
    boolean checkOauthToken(String code,SysUser user,int type);

}
