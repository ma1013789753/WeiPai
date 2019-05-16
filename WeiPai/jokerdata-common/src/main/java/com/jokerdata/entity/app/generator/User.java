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
 * @since 2019-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_user")
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 手机号码，账号
     */
    private String userMobile;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 性别:1男，2女
     */
    private int userGender;

    /**
     * 正常0,1禁用
     */
    private int userState;

    private String addTime;

    /**
     * 支付密码
     */
    private String userPayPwd;

    /**
     * 微信openid
     */
    private String wechatOpenid;

    /**
     * 支付宝账号
     */
    private String alipayAccount;

    /**
     * 预存款
     */
    private BigDecimal availablePredeposit;

    /**
     * 冻结预存款
     */
    private BigDecimal freezePredeposit;

    /**
     * 积分
     */
    private Integer userCoin;

    /**
     * 连续签到天数
     */
    private Integer signLimit;

    /**
     * 累计积分
     */
    private Integer coinTotal;

    private String userQq;

    private String userEmail;

    private String userWx;

    /**
     * 支付宝账户人
     */
    private String alipayName;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
