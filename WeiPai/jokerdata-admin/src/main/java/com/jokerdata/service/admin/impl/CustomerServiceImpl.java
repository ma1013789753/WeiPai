package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.mapper.admin.custom.CustomerMapper;
import com.jokerdata.service.admin.CustomerService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * [前台用户] 用户表 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@Service
public class CustomerServiceImpl  extends ServiceImpl<CustomerMapper, CustomerUser> implements CustomerService {

    @Resource
    CustomerMapper customerMapper;

    /**
     * 分页获取用户列表
     * @param page
     * @return
     */
    @Override
    public MyPage<CustomerUser> selectUserPage(MyPage page) {
        return customerMapper.selectPageVo(page);
    }

    @Override
    public MyPage<CustomerUser> selectRankingPage(MyPage page) {
        return customerMapper.selectRankingPageVo(page);
    }
}
