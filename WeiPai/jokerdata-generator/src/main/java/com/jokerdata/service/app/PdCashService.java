package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.PdCash;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款提现记录表 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface PdCashService extends IService<PdCash> {

     MyPage<PdCash> selectPage(MyPage page);
}
