package com.jokerdata.service.app;

import com.jokerdata.entity.app.generator.Admin;
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
public interface AdminService extends IService<Admin> {

     MyPage<Admin> selectPage(MyPage page);
}
