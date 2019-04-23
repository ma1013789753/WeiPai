package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.Cms;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-23
 */
public interface CmsService extends IService<Cms> {

     MyPage<Cms> selectPage(MyPage page);
}
