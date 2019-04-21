package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.PdLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款变更日志表 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface PdLogService extends IService<PdLog> {

     MyPage<PdLog> selectPage(MyPage page);
}
