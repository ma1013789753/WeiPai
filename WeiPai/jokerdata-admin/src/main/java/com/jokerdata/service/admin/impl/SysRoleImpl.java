package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.entity.admin.custom.SysRoleCustom;
import com.jokerdata.entity.admin.generator.SysRole;
import com.jokerdata.mapper.admin.custom.SysRoleCustomMapper;
import com.jokerdata.mapper.admin.generator.SysRoleMapper;
import com.jokerdata.service.admin.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.ParamSome;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * [权限管理] 角色表 服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@Service
public class SysRoleImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    SysRoleCustomMapper sysRoleCustomMapper;


    public MyPage<SysRoleCustom> selectRolePage(MyPage<SysRoleCustom> page) {
        //多表查询只能手动构造
        ParamSome paramSome = new ParamSome();
        paramSome.setStart((page.getCurrent()-1)*page.getSize());
        paramSome.setEnd(page.getSize());
        paramSome.setSearch1(page.search1);
        List<SysRoleCustom> datas = sysRoleCustomMapper.selectPage(paramSome);
//
//        datas.forEach(role -> {
//            Set<MenuVo> menuVos = new HashSet<>();
//            role.getPermissions().forEach(permission -> {
//                menuVos.add(new MenuVo(permission.getPid(), permission.getFather(), permission.getIcon(), permission.getResources(), permission.getTitle()));
//            });
//            role.setMenuVos(TreeBuilder.findRoots(menuVos));
//        });


        int size = sysRoleCustomMapper.selectCount(new QueryWrapper<>());
        page.setTotal(size);
        if (size%page.size>0) {
            page.setPages(size/page.size+1);
        }else{
            page.setPages(size/page.size);
        }
        page.setRecords(datas);
        return page;
    }

    @Override
    public int insertOrUpdate(SysRole sysRole) {

        return sysRoleCustomMapper.insert(sysRole);
    }


}
