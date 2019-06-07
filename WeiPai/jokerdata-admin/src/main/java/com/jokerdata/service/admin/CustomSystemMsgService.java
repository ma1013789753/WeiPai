package com.jokerdata.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.vo.MyPage;


/**
 * <p>
 * 系统信息 服务接口
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
public interface CustomSystemMsgService extends IService<SystemMsg> {
    MyPage<SystemMsg> getMsgPage(MyPage page);
}
