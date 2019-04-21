package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.mapper.app.generator.CoinMapper;
import com.jokerdata.service.app.CoinService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 预存款充值表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class CoinImpl extends ServiceImpl<CoinMapper, Coin> implements CoinService {
    @Resource
    CoinMapper targetMapper;

    @Override
    public MyPage<Coin> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
