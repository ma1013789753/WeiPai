package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.AuthRule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface AuthRuleService extends IService<AuthRule> {

     MyPage<AuthRule> selectPage(MyPage page);
}
