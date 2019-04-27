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
@TableName("g_share")
public class Share extends Model<Share> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "share_id", type = IdType.AUTO)
    private Integer shareId;

    private Integer userId;

    private String userName;

    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 分享内容   {base64}
     */
    private String shareContent;

    /**
     * (json)
     */
    private String shareImage;

    /**
     * 1:微博；2公众号文章
     */
    private String shareType;

    private String shareUrl;

    /**
     * 分享一次多少积分 现金推广为总金额
     */
    private Integer shareCoin;

    /**
     * 分享多少次
     */
    private Integer shareNum;

    /**
     * 已经分享的人数
     */
    private Integer haveSharedNum;

    /**
     * 总共消耗积分
     */
    private Integer totalCoin;

    /**
     * 是否原创：0否；1是
     */
    private Boolean isOriginal;

    /**
     * 添加时间
     */
    private String addTime;

    private Integer tagId;

    private String tagName;

    /**
     * 默认进行中0， 审核中1， 审核失败2   结束3     取消4
     */
    private Boolean shareState;

    /**
     * 原创转发奖励积分
     */
    private Integer shareExtraCoin;

    /**
     * 0积分  1现金
     */
    private String shareStatus;

    /**
     * video 信息  json
     */
    private String shareVideo;

    /**
     * 微博id
     */
    private String wbId;

    /**
     * 查看次数
     */
    private Integer seeNum;

    /**
     * 默认0  1推荐
     */
    private Integer shareRecommend;

    /**
     * 实际分享次数
     */
    private Integer shareNumTrue;

    /**
     * 最初平均积分
     */
    private String originalCoin;

    /**
     * 0后台  1app
     */
    private Integer fromApp;

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
    private BigDecimal cancelNums;

    /**
     * 背景图
     */
    private String backgroundImage;

    /**
     * 微博短连接
     */
    private String shortUrl;

    /**
     * 微信文章分享封面图
     */
    private String shareImg;


    @Override
    protected Serializable pkVal() {
        return this.shareId;
    }

}
