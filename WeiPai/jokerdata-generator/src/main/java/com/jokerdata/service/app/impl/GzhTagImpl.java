package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.GzhTag;
import com.jokerdata.mapper.app.generator.GzhTagMapper;
import com.jokerdata.service.app.GzhTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 公众号标签 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class GzhTagImpl extends ServiceImpl<GzhTagMapper, GzhTag> implements GzhTagService {
    @Resource
    GzhTagMapper targetMapper;

    @Override
    public MyPage<GzhTag> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
