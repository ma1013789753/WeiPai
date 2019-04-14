package com.jokerdata.service.admin;

import com.jokerdata.entity.admin.generator.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.vo.MyPage;

/**
 * <p>
 * [权限管理] 部门表 服务类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
public interface SysDeptService extends IService<SysDept> {

    MyPage<SysDept> selectRolePage(MyPage<SysDept> page);
}
