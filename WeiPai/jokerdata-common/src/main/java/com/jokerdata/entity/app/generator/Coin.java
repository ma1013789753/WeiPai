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
@TableName("g_coin")
public class Coin extends Model<Coin> {
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "coin_id", type = IdType.AUTO)
    private Integer coin_id;

    /**
     * 记录唯一标示
     */
    private String order_sn;

    /**
     * 会员编号
     */
    private Integer user_id;

    /**
     * 会员名称
     */
    private String user_name;

    /**
     * 充值金额
     */
    private Integer coin_amount;

    /**
     * 订单金额
     */
    private BigDecimal order_amount;

    /**
     * 支付方式1支付宝，2微信，3钱包
     */
    private String pay_type;

    /**
     * 第三方支付接口交易号
     */
    private String pay_out_sn;

    /**
     * 添加时间
     */
    private Integer add_time;

    /**
     * 支付状态 0未支付1支付
     */
    private Boolean pay_state;

    /**
     * 支付时间
     */
    private Integer pay_time;

    /**
     * 管理员名
     */
    private String pdr_admin;


    @Override
    protected Serializable pkVal() {
        return this.coin_id;
    }

}
