package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.Share;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface ShareService extends IService<Share> {

     MyPage<Share> selectPage(MyPage page);
}
