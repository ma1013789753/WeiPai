package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.AuthRule;
import com.jokerdata.mapper.app.generator.AuthRuleMapper;
import com.jokerdata.service.app.AuthRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class AuthRuleImpl extends ServiceImpl<AuthRuleMapper, AuthRule> implements AuthRuleService {
    @Resource
    AuthRuleMapper targetMapper;

    @Override
    public MyPage<AuthRule> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
