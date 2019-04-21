package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Admin;
import com.jokerdata.mapper.app.generator.AdminMapper;
import com.jokerdata.service.app.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class AdminImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    AdminMapper targetMapper;

    @Override
    public MyPage<Admin> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
