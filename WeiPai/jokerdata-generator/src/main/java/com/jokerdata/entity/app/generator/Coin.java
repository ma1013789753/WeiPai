package com.jokerdata.entity.app.generator;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
    * 预存款充值表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
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
    private Integer coinId;

    /**
     * 记录唯一标示
     */
    private String orderSn;

    /**
     * 会员编号
     */
    private Integer userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 充值金额
     */
    private Integer coinAmount;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 支付方式1支付宝，2微信，3钱包
     */
    private String payType;

    /**
     * 第三方支付接口交易号
     */
    private String payOutSn;

    /**
     * 添加时间
     */
    private Integer addTime;

    /**
     * 支付状态 0未支付1支付
     */
    private Boolean payState;

    /**
     * 支付时间
     */
    private Integer payTime;

    /**
     * 管理员名
     */
    private String pdrAdmin;


    @Override
    protected Serializable pkVal() {
        return this.coinId;
    }

}
