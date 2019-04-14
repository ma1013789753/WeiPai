package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.custom.SysUserCustom;
import com.jokerdata.entity.admin.generator.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.SysUserVo;

/**
 * <p>
 * [权限管理] 用户表 服务类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    SysUser login(String username, String password);

    SysUserCustom findByUsername(String admin);

    SysUserCustom findById(String uid);

    SysUserVo findUserInfo(String admin);

    MyPage<SysUserCustom> selectRolePage(MyPage page);
}
