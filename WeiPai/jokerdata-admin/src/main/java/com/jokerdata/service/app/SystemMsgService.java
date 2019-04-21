package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 系统信息 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface SystemMsgService extends IService<SystemMsg> {

     MyPage<SystemMsg> selectPage(MyPage page);

     Page<SystemMsg> getMsgList(Integer userId, Integer curpage);
}
