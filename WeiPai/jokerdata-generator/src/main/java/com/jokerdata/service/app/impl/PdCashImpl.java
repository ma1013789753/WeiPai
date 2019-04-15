package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.PdCash;
import com.jokerdata.mapper.app.generator.PdCashCustomMapper;
import com.jokerdata.service.app.PdCashService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款提现记录表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class PdCashImpl extends ServiceImpl<PdCashCustomMapper, PdCash> implements PdCashService {
    @Resource
    PdCashCustomMapper targetMapper;

    @Override
    public MyPage<PdCash> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
