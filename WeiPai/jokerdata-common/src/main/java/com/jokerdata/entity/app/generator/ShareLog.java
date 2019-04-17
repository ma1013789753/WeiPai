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
@TableName("g_share_log")
public class ShareLog extends Model<ShareLog> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sl_id", type = IdType.AUTO)
    private Integer sl_id;

    private Integer user_id;

    private Integer share_id;

    private Integer share_coin;

    /**
     * 奖励资金
     */
    private BigDecimal share_money;

    /**
     * 默认0
     */
    private Boolean share_state;

    private String add_time;

    /**
     * 审核时间
     */
    private String check_time;

    /**
     * 0待审核 1通过 2未通过 3不用审核
     */
    private Integer is_pass;

    /**
     * 0积分  1币
     */
    private Integer reward_type;

    /**
     * 转发词
     */
    private String content;

    /**
     * 转发人账户编号
     */
    private String account_id;

    private Integer math_id;

    private String mid;

    /**
     * 微博主人user_id
     */
    private Integer master_id;

    /**
     * 对应的日志id
     */
    private String log_id;


    @Override
    protected Serializable pkVal() {
        return this.sl_id;
    }

}
