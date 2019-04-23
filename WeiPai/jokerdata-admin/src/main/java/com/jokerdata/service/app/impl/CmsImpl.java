package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Cms;
import com.jokerdata.mapper.app.custom.CmsCustomMapper;
import com.jokerdata.mapper.app.generator.CmsMapper;
import com.jokerdata.service.app.CmsService;
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
 * @since 2019-04-23
 */
@Service
public class CmsImpl extends ServiceImpl<CmsCustomMapper, Cms> implements CmsService {
    @Resource
    CmsCustomMapper targetMapper;

    @Override
    public MyPage<Cms> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
