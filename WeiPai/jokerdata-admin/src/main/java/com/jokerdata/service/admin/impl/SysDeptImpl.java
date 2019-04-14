package com.jokerdata.service.admin.impl;

import com.jokerdata.entity.admin.generator.SysDept;
import com.jokerdata.mapper.admin.custom.SysDeptCustomMapper;
import com.jokerdata.mapper.admin.generator.SysDeptMapper;
import com.jokerdata.service.admin.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * [权限管理] 部门表 服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2018-11-19
 */
@Service
public class SysDeptImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Resource
    SysDeptCustomMapper sysDeptCustomMapper;


    public MyPage<SysDept> selectRolePage(MyPage<SysDept> page) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为非 0 时(默认为 0),分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return sysDeptCustomMapper.selectPageVo(page);
    }

}
