package com.jokerdata.service.admin.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.common.utils.Constants;
import com.jokerdata.mapper.admin.custom.SysUserCustomMapper;
import com.jokerdata.service.admin.TokenService;
import com.alibaba.druid.util.StringUtils;
import com.jokerdata.entity.admin.custom.TokenBean;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.mapper.admin.generator.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private SysUserCustomMapper sysUserCustomMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 生成token并存入redis中
     * @param userId
     * @return
     */
    @Override
    public String createToken(String userId) {
        String token="";
        token= JWT.create().withAudience(userId)
                .sign(Algorithm.HMAC256(Constants.SECRECT));
        SysUser sysUser = sysUserCustomMapper.selectById(userId);
        TokenBean tokenBean = new TokenBean();
        tokenBean.setUserId(userId);
        tokenBean.setToken(token);
        tokenBean.setSysUser(sysUser);
        redisTemplate.opsForValue().set(userId,tokenBean,30,TimeUnit.MINUTES);
        return token;
    }

    /**
     * 退出登录
     * @param userId
     */
    @Override
    public void deleteToken(String userId) {
        redisTemplate.delete(userId);
    }


    /**
     * 获取userId
     * @param token
     * @return
     */
    @Override
    public String getUserId(String token) {
        String userId = "";
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new MyException("token获取用户失败",ConstCode.CODE_401);
        }
        return userId;
    }

    /**
     * 检查接口的token是否存在
     * @param token
     * @return
     */
    @Override
    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(Constants.SECRECT)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw  new MyException("token验证失败",ConstCode.CODE_401);
        }

        String userId = "";
        try {
            userId = JWT.decode(token).getAudience().get(0);
            Object object = redisTemplate.opsForValue().get(userId);
            if(object == null || !(object  instanceof  TokenBean) ){
                return false;
            }
        } catch (JWTDecodeException j) {
            throw new MyException("token获取用户失败",ConstCode.CODE_401);
        }
        return true;
    }

    /**
     * 获取redis用户信息
     * @param userId
     * @return
     */
    @Override
    public SysUser getRedisUser(String userId) {
        Object object = redisTemplate.opsForValue().get(userId);
        if(object == null){
            throw new MyException("用户不存在", ConstCode.CODE_401);
        }
        return ((TokenBean) object).getSysUser();
    }

}
