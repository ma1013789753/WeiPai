package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Math;
import com.jokerdata.mapper.app.generator.MathMapper;
import com.jokerdata.service.app.MathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 奖励预备 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class MathImpl extends ServiceImpl<MathMapper, Math> implements MathService {
    @Resource
    MathMapper targetMapper;

    @Override
    public MyPage<Math> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
