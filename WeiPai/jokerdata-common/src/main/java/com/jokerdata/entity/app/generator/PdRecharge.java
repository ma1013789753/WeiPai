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
    * 预存款充值表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_pd_recharge")
public class PdRecharge extends Model<PdRecharge> {
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "pdr_id", type = IdType.AUTO)
    private Integer pdr_id;

    /**
     * 记录唯一标示
     */
    private String pdr_sn;

    /**
     * 会员编号
     */
    private Integer pdr_member_id;

    /**
     * 会员名称
     */
    private String pdr_member_name;

    /**
     * 充值金额
     */
    private BigDecimal pdr_amount;

    /**
     * 支付方式
     */
    private String pdr_payment_code;

    /**
     * 支付方式
     */
    private String pdr_payment_name;

    /**
     * 第三方支付接口交易号
     */
    private String pdr_trade_sn;

    /**
     * 添加时间
     */
    private Integer pdr_add_time;

    /**
     * 支付状态 0未支付1支付
     */
    private Boolean pdr_payment_state;

    /**
     * 支付时间
     */
    private Integer pdr_payment_time;

    /**
     * 管理员名
     */
    private String pdr_admin;


    @Override
    protected Serializable pkVal() {
        return this.pdr_id;
    }

}
