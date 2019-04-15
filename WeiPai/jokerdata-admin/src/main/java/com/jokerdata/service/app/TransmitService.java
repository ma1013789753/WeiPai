package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Transmit;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface TransmitService extends IService<Transmit> {

     MyPage<Transmit> selectPage(MyPage page);
}
