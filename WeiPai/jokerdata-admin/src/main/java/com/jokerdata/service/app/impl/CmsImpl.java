package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Cms;
import com.jokerdata.mapper.app.generator.CmsMapper;
import com.jokerdata.service.app.CmsService;
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
public class CmsImpl extends ServiceImpl<CmsMapper, Cms> implements CmsService {
    @Resource
    CmsMapper targetMapper;

    @Override
    public MyPage<Cms> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
