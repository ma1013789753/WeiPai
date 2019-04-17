package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("g_user")
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer user_id;

    /**
     * 昵称
     */
    private String user_name;

    /**
     * 手机号码，账号
     */
    private String user_mobile;

    /**
     * 密码
     */
    private String user_password;

    /**
     * 性别:1男，2女
     */
    private int user_gender;

    /**
     * 正常0,1禁用
     */
    private int user_state;

    private String add_time;

    /**
     * 支付密码
     */
    private String user_pay_pwd;

    /**
     * 微信openid
     */
    private String wechat_openid;

    /**
     * 支付宝账号
     */
    private String alipay_account;

    /**
     * 预存款
     */
    private BigDecimal available_predeposit;

    /**
     * 冻结预存款
     */
    private BigDecimal freeze_predeposit;

    /**
     * 积分
     */
    private Integer user_coin;

    /**
     * 连续签到天数
     */
    private Integer sign_limit;

    /**
     * 累计积分
     */
    private Integer coin_total;

    private String user_qq;

    private String user_email;

    private String user_wx;

    /**
     * 支付宝账户人
     */
    private String alipay_name;


    @Override
    protected Serializable pkVal() {
        return this.user_id;
    }

}
