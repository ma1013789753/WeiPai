package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.mapper.app.generator.UserAccountMapper;
import com.jokerdata.parames.vo.UserAccept;
import com.jokerdata.service.app.UserAccountService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class UserAccountImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {
    @Resource
    UserAccountMapper targetMapper;


    @Override
    public List<UserAccept> getUserAccept(String user_id, String share_id) {
        return targetMapper.getUserAccept(user_id,share_id);
    }
}
