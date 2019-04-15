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
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_user_account")
public class UserAccount extends Model<UserAccount> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "account_id", type = IdType.AUTO)
    private Integer accountId;

    private Integer userId;

    /**
     * 账号名
     */
    private String accountName;

    /**
     * 账号头像
     */
    private String accountAvatar;

    /**
     * 微博uid   公众号id    微信号
     */
    private String uid;

    /**
     * 微博OAuth授权    用户id与公众号编号的MD5     微信openid
     */
    private String accessToken;

    /**
     * 小头像
     */
    private String avatarHd;

    /**
     * 等待审核0,审核通过1,审核失败2,账号禁用中-1 ，永久禁用-2
     */
    private Boolean accountState;

    private String addTime;

    /**
     * 禁用解除日期
     */
    private String accountLimit;

    /**
     * 禁用原因
     */
    private String disableReason;

    /**
     * 审核失败原因
     */
    private String nopassReason;

    /**
     * 0微博   1公众号    2微信
     */
    private Integer accType;

    /**
     * 粉丝数
     */
    private Integer followNum;

    /**
     * 粉丝数截图
     */
    private String numScreen;

    /**
     * 公众号二维码
     */
    private String wechatPic;

    /**
     * 微博账号V认证 0未认证 ,1已认证
     */
    private Boolean vLegalize;

    /**
     * 公众号标签id
     */
    private Integer gzhTagId;

    /**
     * 公众号备注
     */
    private String remarks;

    /**
     * 微博用户所在地
     */
    private String location;

    /**
     * 微博用户性别 m 男, f 女 , n 未知
     */
    private String gender;

    /**
     * 微博关注数
     */
    private Integer friendsCount;

    /**
     * 微博数
     */
    private Integer statusesCount;

    /**
     * 微博账号注册时间
     */
    private String createdAt;


    @Override
    protected Serializable pkVal() {
        return this.accountId;
    }

}
