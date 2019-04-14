package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Spare;
import com.jokerdata.mapper.app.custom.SpareCustomMapper;
import com.jokerdata.service.app.SpareService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@Service
public class SpareImpl extends ServiceImpl<SpareCustomMapper, Spare> implements SpareService {
    @Resource
    SpareCustomMapper targetMapper;

    @Override
    public MyPage<Spare> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
