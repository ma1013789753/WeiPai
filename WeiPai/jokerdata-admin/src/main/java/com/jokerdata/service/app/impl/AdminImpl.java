package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Admin;
import com.jokerdata.mapper.app.generator.AdminCustomMapper;
import com.jokerdata.service.app.AdminService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class AdminImpl extends ServiceImpl<AdminCustomMapper, Admin> implements AdminService {
    @Resource
    AdminCustomMapper targetMapper;

    @Override
    public MyPage<Admin> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
