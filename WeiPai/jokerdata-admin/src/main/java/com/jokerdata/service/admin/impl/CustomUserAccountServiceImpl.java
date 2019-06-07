package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.mapper.app.generator.UserAccountMapper;
import com.jokerdata.service.admin.CustomUserAccountService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  用户第三方账号服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
@Service
public class CustomUserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements CustomUserAccountService {

}
