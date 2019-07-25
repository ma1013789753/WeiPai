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
@TableName("g_share_log")
public class ShareLog extends Model<ShareLog> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sl_id", type = IdType.AUTO)
    private Integer slId;

    private Integer userId;

    private Integer shareId;

    private Integer shareCoin;

    /**
     * 奖励资金
     */
    private BigDecimal shareMoney;

    /**
     * 默认0
     */
    private Boolean shareState;

    private String addTime;

    /**
     * 审核时间
     */
    private String checkTime;

    /**
     * 0待审核 1通过 2未通过 3不用审核
     */
    private Integer isPass;

    /**
     * 0积分  1币
     */
    private Integer rewardType;

    /**
     * 转发词
     */
    private String content;

    /**
     * 转发人账户编号
     */
    private String accountId;

    private Integer mathId;

    private String mid;

    /**
     * 微博主人user_id
     */
    private Integer masterId;

    /**
     * 对应的日志id
     */
    private String logId;


    @Override
    protected Serializable pkVal() {
        return this.slId;
    }



}
