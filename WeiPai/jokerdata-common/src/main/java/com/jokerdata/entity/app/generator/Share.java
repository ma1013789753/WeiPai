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
@TableName("g_share")
public class Share extends Model<Share> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "share_id", type = IdType.AUTO)
    private Integer share_id;

    private Integer user_id;

    private String user_name;

    /**
     * 账号id
     */
    private Integer account_id;

    /**
     * 分享内容   {base64}
     */
    private String share_content;

    /**
     * (json)
     */
    private String share_image;

    /**
     * 1:微博；2公众号文章
     */
    private Boolean share_type;

    private String share_url;

    /**
     * 分享一次多少积分 现金推广为总金额
     */
    private Integer share_coin;

    /**
     * 分享多少次
     */
    private Integer share_num;

    /**
     * 已经分享的人数
     */
    private Integer have_shared_num;

    /**
     * 总共消耗积分
     */
    private Integer total_coin;

    /**
     * 是否原创：0否；1是
     */
    private Boolean is_original;

    /**
     * 添加时间
     */
    private String add_time;

    private Integer tag_id;

    private String tag_name;

    /**
     * 默认进行中0， 审核中1， 审核失败2   结束3     取消4
     */
    private Boolean share_state;

    /**
     * 原创转发奖励积分
     */
    private Integer share_extra_coin;

    /**
     * 0积分  1现金
     */
    private String share_status;

    /**
     * video 信息  json
     */
    private String share_video;

    /**
     * 微博id
     */
    private String wb_id;

    /**
     * 查看次数
     */
    private Integer see_num;

    /**
     * 默认0  1推荐
     */
    private Integer share_recommend;

    /**
     * 实际分享次数
     */
    private Integer share_num_true;

    /**
     * 最初平均积分
     */
    private String original_coin;

    /**
     * 0后台  1app
     */
    private Integer from_app;

    /**
     * 截止时间
     */
    private String expires;

    /**
     * 其他信息json
     */
    private String json;

    /**
     * 取消数额
     */
    private BigDecimal cancel_nums;

    /**
     * 背景图
     */
    private String background_image;

    /**
     * 微博短连接
     */
    private String short_url;

    /**
     * 微信文章分享封面图
     */
    private String share_img;


    @Override
    protected Serializable pkVal() {
        return this.share_id;
    }

}
