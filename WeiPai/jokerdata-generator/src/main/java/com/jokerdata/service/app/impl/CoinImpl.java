package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.mapper.app.generator.CoinCustomMapper;
import com.jokerdata.service.app.CoinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款充值表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class CoinImpl extends ServiceImpl<CoinCustomMapper, Coin> implements CoinService {
    @Resource
    CoinCustomMapper targetMapper;

    @Override
    public MyPage<Coin> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
