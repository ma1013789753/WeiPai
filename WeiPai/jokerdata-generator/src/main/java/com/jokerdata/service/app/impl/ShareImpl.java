package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.mapper.app.generator.ShareMapper;
import com.jokerdata.service.app.ShareService;
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
 * @since 2019-04-20
 */
@Service
public class ShareImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {
    @Resource
    ShareMapper targetMapper;

    @Override
    public MyPage<Share> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
