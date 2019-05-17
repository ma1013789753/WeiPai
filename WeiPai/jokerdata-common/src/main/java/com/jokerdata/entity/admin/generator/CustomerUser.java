package com.jokerdata.entity.admin.generator;

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
 * @author aozhang
 * @since 2019-05-1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_user")
public class CustomerUser extends Model<CustomerUser> {
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
    private Boolean userGender;

    /**
     * 正常0,1禁用
     */
    private Boolean userState;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getUserGender() {
        return userGender;
    }

    public void setUserGender(Boolean userGender) {
        this.userGender = userGender;
    }

    public Boolean getUserState() {
        return userState;
    }

    public void setUserState(Boolean userState) {
        this.userState = userState;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUserPayPwd() {
        return userPayPwd;
    }

    public void setUserPayPwd(String userPayPwd) {
        this.userPayPwd = userPayPwd;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public BigDecimal getAvailablePredeposit() {
        return availablePredeposit;
    }

    public void setAvailablePredeposit(BigDecimal availablePredeposit) {
        this.availablePredeposit = availablePredeposit;
    }

    public BigDecimal getFreezePredeposit() {
        return freezePredeposit;
    }

    public void setFreezePredeposit(BigDecimal freezePredeposit) {
        this.freezePredeposit = freezePredeposit;
    }

    public Integer getUserCoin() {
        return userCoin;
    }

    public void setUserCoin(Integer userCoin) {
        this.userCoin = userCoin;
    }

    public Integer getSignLimit() {
        return signLimit;
    }

    public void setSignLimit(Integer signLimit) {
        this.signLimit = signLimit;
    }

    public Integer getCoinTotal() {
        return coinTotal;
    }

    public void setCoinTotal(Integer coinTotal) {
        this.coinTotal = coinTotal;
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserWx() {
        return userWx;
    }

    public void setUserWx(String userWx) {
        this.userWx = userWx;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }
}
