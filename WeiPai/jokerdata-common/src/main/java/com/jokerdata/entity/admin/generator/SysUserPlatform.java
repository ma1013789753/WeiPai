package com.jokerdata.entity.admin.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * [权限管理] 用户账号与第三方登陆信息对应表
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_user_platform")
public class SysUserPlatform extends Model<SysUserPlatform> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    /**
     * 用户id
     */
    private Integer userUid;

    /**
     * 第三方标识
     */
    private String unionId;

    /**
     * 第三方平台
     */
    private Integer platform;

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
     * 状态
     */
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.tid;
    }

}
