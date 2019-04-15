package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款充值表 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface CoinService extends IService<Coin> {

     MyPage<Coin> selectPage(MyPage page);
}
