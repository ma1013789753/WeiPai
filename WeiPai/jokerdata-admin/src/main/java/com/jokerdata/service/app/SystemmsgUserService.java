package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.SystemmsgUser;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 系统消息用户 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface SystemmsgUserService extends IService<SystemmsgUser> {

     MyPage<SystemmsgUser> selectPage(MyPage page);
}
