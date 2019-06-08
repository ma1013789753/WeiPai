package com.jokerdata.service.admin.impl;

import com.jokerdata.common.utils.TreeBuilder;
import com.jokerdata.entity.admin.custom.SysRoleCustom;
import com.jokerdata.entity.admin.custom.SysUserCustom;
import com.jokerdata.entity.admin.generator.SysUser;
import com.jokerdata.mapper.admin.custom.SysRoleCustomMapper;
import com.jokerdata.mapper.admin.custom.SysUserCustomMapper;
import com.jokerdata.mapper.admin.generator.SysUserMapper;
import com.jokerdata.service.admin.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.vo.ButtonVo;
import com.jokerdata.vo.MenuVo;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.SysUserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * [权限管理] 用户表 服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    SysUserCustomMapper sysUserCustomMapper;

    @Resource
    SysRoleCustomMapper sysRoleCustomMapper;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public SysUser login(String username, String password) {

        SysUserCustom userCustom = findByUsername(username);
        if(userCustom==null){
            return null;
        }
        if(DigestUtils.sha256Hex(password).equals(userCustom.getPassword())){
            return userCustom;
        }
        return null;
    }



    @Override
    public SysUserCustom findByUsername(String admin) {

        SysUserCustom sysUser = sysUserCustomMapper.selectByUserName(admin);

        return sysUser;
    }

    @Override
    public SysUserCustom findById(String uid) {
        return sysUserCustomMapper.selectUserById(uid);
    }

    @Override
    public SysUserVo findUserInfo(String admin) {
        SysUserCustom sysUser = findByUsername(admin);
        Set<SysRoleCustom> sysRoles = sysRoleCustomMapper.selectByUserName(admin);
        Set<ButtonVo> buttonVos = new HashSet<>();
        Set<MenuVo> menuVos = new HashSet<>();
        for (SysRoleCustom sysRole: sysRoles) {
            sysRole.getPermissions().forEach(permission -> {
                    if (permission.getPid()!=null){
                        menuVos.add(new MenuVo(permission.getPid(), permission.getFather(), permission.getIcon(), permission.getResources(), permission.getTitle()));
                    }
            });
        }

        SysUserVo sysUserVo =
                new SysUserVo(sysUser.getUid(),
                        sysUser.getAvatar(),
                        sysUser.getNickname(),
                        sysUser.getUsername(),
                        sysUser.getMail(),
                        sysUser.getCreateTime(),
                        sysUser.getRole(),
                        buttonVos,
                        TreeBuilder.findRoots(menuVos));
        return sysUserVo;
    }

    @Override
    public MyPage<SysUserCustom> selectRolePage(MyPage page) {

        return sysUserCustomMapper.selectPageVo(page);
    }
}
