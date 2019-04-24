package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Article;
import com.jokerdata.mapper.app.generator.ArticleMapper;
import com.jokerdata.service.app.ArticleService;
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
 * @since 2019-04-24
 */
@Service
public class ArticleImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    ArticleMapper targetMapper;

    @Override
    public MyPage<Article> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
