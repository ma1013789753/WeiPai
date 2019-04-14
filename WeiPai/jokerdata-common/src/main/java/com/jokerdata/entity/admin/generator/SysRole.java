package com.jokerdata.entity.admin.generator;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [权限管理] 角色表
 * </p>
 *
 * @author oldMa
 * @since 2018-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统角色ID
     */
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    /**
     * 系统角色描述
     */
    private String memo;

    /**
     * 系统角色名称
     */
    private String name;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 系统角色状态[0.删除，1.正常]
     */
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.rid;
    }

}
