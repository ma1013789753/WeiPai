package com.jokerdata.service.admin.impl;

import com.jokerdata.entity.admin.generator.SysPermission;
import com.jokerdata.mapper.admin.custom.SysPermissionCustomMapper;
import com.jokerdata.mapper.admin.generator.SysPermissionMapper;
import com.jokerdata.service.admin.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * [权限管理] 权限表 服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@Service
public class SysPermissionImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Resource
    SysPermissionCustomMapper sysPermissionCustomMapper;

    @Override
    public MyPage<SysPermission> selectRolePage(MyPage page) {

        return sysPermissionCustomMapper.selectPageVo(page);
    }
}
