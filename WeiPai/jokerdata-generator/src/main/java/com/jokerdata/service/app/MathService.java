package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.Math;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 奖励预备 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface MathService extends IService<Math> {

     MyPage<Math> selectPage(MyPage page);
}
