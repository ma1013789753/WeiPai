package com.jokerdata.service.admin.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.HttpClientUtil;
import com.jokerdata.common.JsonUtils;
import com.jokerdata.common.config.OauthCofig;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.common.utils.Constants;
import com.jokerdata.entity.admin.generator.SysUserPlatform;
import com.jokerdata.mapper.admin.custom.SysUserCustomMapper;
import com.jokerdata.mapper.admin.generator.SysUserPlatformMapper;
import com.jokerdata.service.admin.TokenService;
import com.alibaba.druid.util.StringUtils;
import com.jokerdata.entity.admin.custom.TokenBean;
import com.jokerdata.entity.admin.generator.SysUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private SysUserCustomMapper sysUserCustomMapper;

    @Resource
    private SysUserPlatformMapper sysUserPlatformMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OauthCofig oauthCofig;

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

    /**
     * 通过第三方获取授权用户unionid
     * @param code
     * @return
     */
    @Override
    public String getOauthToken(String code) {
        try {
            String res = HttpClientUtil.doGet(oauthCofig.getWechat_BaseURI() +
                    "?appid=" + oauthCofig.getAppID() + "&secret=" + oauthCofig.getAppSecret() + "&code="
                    + code + "&grant_type=authorization_code");
            Map map = JsonUtils.jsonToPojo(res, Map.class);
            String unionid = "";
            unionid = (String) map.get("unionid");
            return unionid;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查第三方获取授权用户unionid
     * @param code
     * @param user
     * @return
     */
    @Override
    public boolean checkOauthToken(String code, SysUser user,int type) {
        String oauthToken = getOauthToken(code);
        QueryWrapper queryWrapper = new QueryWrapper();
        //创建查询条件 uid=?
        queryWrapper.eq("user_uid",user.getUid());
        queryWrapper.eq("platform",type);
        queryWrapper.eq("status",0);
        SysUserPlatform sysUserPlatform = sysUserPlatformMapper.selectOne(queryWrapper);
        if (sysUserPlatform == null) {
            sysUserPlatform = new SysUserPlatform();
            sysUserPlatform.setUserUid(user.getUid());
            sysUserPlatform.setUnionId(DigestUtils.sha256Hex(oauthToken));
            sysUserPlatform.setPlatform(type);
            sysUserPlatform.setCreateTime(new Date());
            sysUserPlatform.setUpdateTime(new Date());
            sysUserPlatform.setStatus(0);
            //新增第三方账号绑定记录
            int r = sysUserPlatformMapper.insert(sysUserPlatform);
            if (r > 0) {
                return true;
            }
            return false;
        }
        if (!StringUtils.isEmpty(oauthToken) && DigestUtils.sha256Hex(oauthToken).equals(sysUserPlatform.getUnionId())) {
            return true;
        }
        return false;
    }

}
