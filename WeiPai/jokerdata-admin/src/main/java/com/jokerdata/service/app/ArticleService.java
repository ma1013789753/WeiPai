package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Article;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface ArticleService extends IService<Article> {

    Article getArticleByCode(String sign_rule);
}
