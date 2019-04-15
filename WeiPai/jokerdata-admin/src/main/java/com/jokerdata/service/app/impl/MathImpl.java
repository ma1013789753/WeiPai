package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Math;
import com.jokerdata.mapper.app.generator.MathCustomMapper;
import com.jokerdata.service.app.MathService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 奖励预备 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class MathImpl extends ServiceImpl<MathCustomMapper, Math> implements MathService {
    @Resource
    MathCustomMapper targetMapper;

    @Override
    public MyPage<Math> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
