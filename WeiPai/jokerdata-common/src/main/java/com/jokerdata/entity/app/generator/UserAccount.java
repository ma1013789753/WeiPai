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
@TableName("g_user_account")
public class UserAccount extends Model<UserAccount> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "account_id", type = IdType.AUTO)
    private Integer account_id;

    private Integer user_id;

    /**
     * 账号名
     */
    private String account_name;

    /**
     * 账号头像
     */
    private String account_avatar;

    /**
     * 微博uid   公众号id    微信号
     */
    private String uid;

    /**
     * 微博OAuth授权    用户id与公众号编号的MD5     微信openid
     */
    private String access_token;

    /**
     * 小头像
     */
    private String avatar_hd;

    /**
     * 等待审核0,审核通过1,审核失败2,账号禁用中-1 ，永久禁用-2
     */
    private Boolean account_state;

    private String add_time;

    /**
     * 禁用解除日期
     */
    private String account_limit;

    /**
     * 禁用原因
     */
    private String disable_reason;

    /**
     * 审核失败原因
     */
    private String nopass_reason;

    /**
     * 0微博   1公众号    2微信
     */
    private Integer acc_type;

    /**
     * 粉丝数
     */
    private Integer follow_num;

    /**
     * 粉丝数截图
     */
    private String num_screen;

    /**
     * 公众号二维码
     */
    private String wechat_pic;

    /**
     * 微博账号V认证 0未认证 ,1已认证
     */
    private Boolean v_legalize;

    /**
     * 公众号标签id
     */
    private Integer gzh_tag_id;

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
    private Integer friends_count;

    /**
     * 微博数
     */
    private Integer statuses_count;

    /**
     * 微博账号注册时间
     */
    private String created_at;


    @Override
    protected Serializable pkVal() {
        return this.account_id;
    }

}
