package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.ShareTag;
import com.jokerdata.mapper.app.generator.ShareTagMapper;
import com.jokerdata.service.app.ShareTagService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class ShareTagImpl extends ServiceImpl<ShareTagMapper, ShareTag> implements ShareTagService {
    @Resource
    ShareTagMapper targetMapper;

    @Override
    public MyPage<ShareTag> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
