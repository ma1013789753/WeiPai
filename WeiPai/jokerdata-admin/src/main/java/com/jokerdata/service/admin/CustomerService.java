package com.jokerdata.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.vo.MyPage;

import java.io.Serializable;

/**
 * <p>
 * [前台用户] 用户表 服务类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
public interface CustomerService extends IService<CustomerUser> {

    MyPage<CustomerUser> selectUserPage(MyPage page);
    MyPage<CustomerUser> selectRankingPage(MyPage page);
}
