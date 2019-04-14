package com.jokerdata.entity.admin.custom;

import com.jokerdata.entity.admin.generator.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * redis缓存登录用户
 */
@Data
public class TokenBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String token;

    private SysUser sysUser;

}
