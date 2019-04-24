package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.AuthGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface AuthGroupService extends IService<AuthGroup> {

     MyPage<AuthGroup> selectPage(MyPage page);
}
