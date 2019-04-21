package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.PdCash;
import com.jokerdata.mapper.app.generator.PdCashMapper;
import com.jokerdata.service.app.PdCashService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 预存款提现记录表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class PdCashImpl extends ServiceImpl<PdCashMapper, PdCash> implements PdCashService {
    @Resource
    PdCashMapper targetMapper;

    @Override
    public MyPage<PdCash> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
