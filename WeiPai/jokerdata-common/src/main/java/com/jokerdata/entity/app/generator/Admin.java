package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
    * 
    * </p>
 *
 * @author oldMa
 * @since 2019-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_admin")
public class Admin extends Model<Admin> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer admin_id;

    /**
     * 管理员帐号
     */
    private String admin_name;

    /**
     * 帐号密码
     */
    private String admin_password;

    /**
     * 最后登陆时间
     */
    private Integer admin_logintime;

    /**
     * 登陆次数
     */
    private Integer admin_loginnum;

    /**
     * 管理员头像
     */
    private String admin_avatar;

    /**
     * 0 允许登陆  1禁止登陆
     */
    private Boolean admin_state;

    /**
     * 超级管理员 1是
     */
    private Boolean admin_is_super;


    @Override
    protected Serializable pkVal() {
        return this.admin_id;
    }

}
