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
 * [权限管理] 权限表
 * </p>
 *
 * @author oldMa
 * @since 2018-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_permission")
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限唯一ID
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 上级ID
     */
    private Integer father;

    /**
     * 权限资源
     */
    private String resources;

    /**
     * 资源名称
     */
    private String title;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 类型，menu或者button
     */
    private String type;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 权限描述
     */
    private String memo;

    /**
     * 1 未删除，0删除
     */
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.pid;
    }

}
