package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.mapper.app.generator.ShareCustomMapper;
import com.jokerdata.service.app.ShareService;
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
public class ShareImpl extends ServiceImpl<ShareCustomMapper, Share> implements ShareService {
    @Resource
    ShareCustomMapper targetMapper;

    @Override
    public MyPage<Share> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
