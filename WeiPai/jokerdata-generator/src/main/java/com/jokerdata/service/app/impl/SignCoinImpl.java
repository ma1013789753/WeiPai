package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.SignCoin;
import com.jokerdata.mapper.app.generator.SignCoinMapper;
import com.jokerdata.service.app.SignCoinService;
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
 * @since 2019-04-24
 */
@Service
public class SignCoinImpl extends ServiceImpl<SignCoinMapper, SignCoin> implements SignCoinService {
    @Resource
    SignCoinMapper targetMapper;

    @Override
    public MyPage<SignCoin> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
