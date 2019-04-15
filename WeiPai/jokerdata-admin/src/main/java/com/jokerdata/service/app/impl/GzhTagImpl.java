package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.GzhTag;
import com.jokerdata.mapper.app.generator.GzhTagCustomMapper;
import com.jokerdata.service.app.GzhTagService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 公众号标签 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class GzhTagImpl extends ServiceImpl<GzhTagCustomMapper, GzhTag> implements GzhTagService {
    @Resource
    GzhTagCustomMapper targetMapper;

    @Override
    public MyPage<GzhTag> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
