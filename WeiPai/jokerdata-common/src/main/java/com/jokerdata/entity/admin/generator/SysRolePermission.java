package com.jokerdata.entity.admin.generator;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [权限管理] 角色和权限表
 * </p>
 *
 * @author oldMa
 * @since 2018-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_role_permission")
public class SysRolePermission extends Model<SysRolePermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    /**
     * 权限ID
     */
    private Integer perPid;

    /**
     * 角色ID
     */
    private Integer roleRid;


    @Override
    protected Serializable pkVal() {
        return this.tid;
    }

}
