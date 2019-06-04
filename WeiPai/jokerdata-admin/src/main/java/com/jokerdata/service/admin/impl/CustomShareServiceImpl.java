package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.mapper.app.generator.ShareMapper;
import com.jokerdata.service.admin.CustomShareService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * [互推管理]  服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-28
 */
@Service
public class CustomShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements CustomShareService {
    @Resource
    ShareMapper shareMapper;

    @Override
    public MyPage<Share> getSharePage(MyPage page, int typeId) {
        return shareMapper.selectPageVo(page,typeId);
    }

    @Override
    public Share getShareById(int shareId) {
        return shareMapper.selectById(shareId);
    }
}
