package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.SignCoin;
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
public interface SignCoinService extends IService<SignCoin> {

     MyPage<SignCoin> selectPage(MyPage page);
}
