package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.generator.SysUser;

public interface TokenService {

    String createToken(String userId);

    void deleteToken(String userId);

    String getUserId(String token);

    boolean checkToken(String token);

    SysUser getRedisUser(String userId);
}
