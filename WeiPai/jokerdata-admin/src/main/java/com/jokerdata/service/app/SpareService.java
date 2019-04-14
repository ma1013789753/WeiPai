package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Spare;
import com.jokerdata.vo.MyPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
public interface SpareService extends IService<Spare> {

     MyPage<Spare> selectPage(MyPage page);
}
