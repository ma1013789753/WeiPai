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
 * [权限管理] 部门表
 * </p>
 *
 * @author oldMa
 * @since 2018-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门排序
     */
    private Integer sort;

    /**
     * 部门描述
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0 删除，1未删除
     */
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.tid;
    }

}
