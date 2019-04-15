package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.ShareTag;
import com.jokerdata.mapper.app.generator.ShareTagCustomMapper;
import com.jokerdata.service.app.ShareTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

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
