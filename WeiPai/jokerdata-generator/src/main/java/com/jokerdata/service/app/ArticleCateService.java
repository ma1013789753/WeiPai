package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.ArticleCate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface ArticleCateService extends IService<ArticleCate> {

     MyPage<ArticleCate> selectPage(MyPage page);
}
