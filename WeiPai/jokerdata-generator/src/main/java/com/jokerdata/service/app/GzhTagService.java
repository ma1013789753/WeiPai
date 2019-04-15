package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.GzhTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 公众号标签 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface GzhTagService extends IService<GzhTag> {

     MyPage<GzhTag> selectPage(MyPage page);
}
