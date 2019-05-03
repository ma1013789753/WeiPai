package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Article;
import com.jokerdata.mapper.app.generator.ArticleMapper;
import com.jokerdata.service.app.ArticleService;
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
public class ArticleImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    ArticleMapper targetMapper;

    /**
     * 根据code获取文章
     * @param sign_rule
     * @return
     */
    @Override
    public Article getArticleByCode(String sign_rule) {
        return this.getOne(new QueryWrapper<Article>().eq("article_code",sign_rule));
    }
}
