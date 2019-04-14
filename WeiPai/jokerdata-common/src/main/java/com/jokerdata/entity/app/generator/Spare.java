package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author oldMa
 * @since 2019-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_spare")
public class Spare extends Model<Spare> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 备品名称
     */
    private String name;

    /**
     * 编号
     */
    private String code;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 在库数量
     */
    private Integer number;

    /**
     * 警报线
     */
    private Integer warnLine;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 负责人
     */
    private String user;

    /**
     * 购入周期
     */
    private Integer round;

    /**
     * 摆放位置
     */
    private String address;

    /**
     * 删除状态 1删除
     */
    @TableLogic
    private Integer del;

    private Date updateTime;

    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
