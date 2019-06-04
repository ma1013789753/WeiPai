package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.mapper.app.generator.CoinMapper;
import com.jokerdata.service.admin.CustomCoinService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 积分预存款充值表 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-06-02
 */
@Service
public class CustomCoinServiceImpl extends ServiceImpl<CoinMapper, Coin> implements CustomCoinService {

}
