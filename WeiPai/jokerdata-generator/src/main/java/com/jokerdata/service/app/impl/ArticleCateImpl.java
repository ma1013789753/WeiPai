package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.ArticleCate;
import com.jokerdata.mapper.app.generator.ArticleCateCustomMapper;
import com.jokerdata.service.app.ArticleCateService;
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
public class ArticleCateImpl extends ServiceImpl<ArticleCateCustomMapper, ArticleCate> implements ArticleCateService {
    @Resource
    ArticleCateCustomMapper targetMapper;

    @Override
    public MyPage<ArticleCate> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
