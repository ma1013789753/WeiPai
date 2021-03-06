package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.PdLog;
import com.jokerdata.vo.MyPage;

import java.math.BigDecimal;

/**
 * <p>
    * 预存款变更日志表 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface PdLogService extends IService<PdLog> {

    BigDecimal getAllCount(Integer shareId);
}
