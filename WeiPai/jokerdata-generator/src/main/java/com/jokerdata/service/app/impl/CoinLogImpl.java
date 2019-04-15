package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.CoinLog;
import com.jokerdata.mapper.app.generator.CoinLogCustomMapper;
import com.jokerdata.service.app.CoinLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class CoinLogImpl extends ServiceImpl<CoinLogCustomMapper, CoinLog> implements CoinLogService {
    @Resource
    CoinLogCustomMapper targetMapper;

    @Override
    public MyPage<CoinLog> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
