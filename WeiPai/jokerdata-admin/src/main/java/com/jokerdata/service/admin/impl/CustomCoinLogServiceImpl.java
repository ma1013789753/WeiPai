package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.CoinLog;
import com.jokerdata.mapper.app.generator.CoinLogMapper;
import com.jokerdata.service.admin.CustomCoinLogService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  积分流水服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-06-02
 */
@Service
public class CustomCoinLogServiceImpl extends ServiceImpl<CoinLogMapper, CoinLog> implements CustomCoinLogService {

}
