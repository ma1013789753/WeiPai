package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Transmit;
import com.jokerdata.mapper.app.generator.TransmitCustomMapper;
import com.jokerdata.service.app.TransmitService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class TransmitImpl extends ServiceImpl<TransmitCustomMapper, Transmit> implements TransmitService {
    @Resource
    TransmitCustomMapper targetMapper;

    @Override
    public MyPage<Transmit> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
