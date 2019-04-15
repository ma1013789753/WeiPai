package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.ShareTag;
import com.jokerdata.mapper.app.generator.ShareTagCustomMapper;
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
 * @since 2019-04-15
 */
@Service
public class ShareTagImpl extends ServiceImpl<ShareTagCustomMapper, ShareTag> implements ShareTagService {
    @Resource
    ShareTagCustomMapper targetMapper;

    @Override
    public MyPage<ShareTag> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
