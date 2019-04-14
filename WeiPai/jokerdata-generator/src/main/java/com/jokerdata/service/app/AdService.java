package com.jokerdata.service;

import com.jokerdata.entity.app.generator.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oldMa
 * @since 2019-04-14
 */
public interface AdService extends IService<Ad> {

     MyPage<Ad> selectPage(MyPage page);
}
