package com.jokerdata.entity.app.generator;

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
    * 
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_admin")
public class Admin extends Model<Admin> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    /**
     * 管理员帐号
     */
    private String adminName;

    /**
     * 帐号密码
     */
    private String adminPassword;

    /**
     * 最后登陆时间
     */
    private Integer adminLogintime;

    /**
     * 登陆次数
     */
    private Integer adminLoginnum;

    /**
     * 管理员头像
     */
    private String adminAvatar;

    /**
     * 0 允许登陆  1禁止登陆
     */
    private Boolean adminState;

    /**
     * 超级管理员 1是
     */
    private Boolean adminIsSuper;


    @Override
    protected Serializable pkVal() {
        return this.adminId;
    }

}
