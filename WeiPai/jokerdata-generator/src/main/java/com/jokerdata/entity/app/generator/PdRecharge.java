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
@TableName("g_pd_recharge")
public class PdRecharge extends Model<PdRecharge> {
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "pdr_id", type = IdType.AUTO)
    private Integer pdrId;

    /**
     * 记录唯一标示
     */
    private String pdrSn;

    /**
     * 会员编号
     */
    private Integer pdrMemberId;

    /**
     * 会员名称
     */
    private String pdrMemberName;

    /**
     * 充值金额
     */
    private BigDecimal pdrAmount;

    /**
     * 支付方式
     */
    private String pdrPaymentCode;

    /**
     * 支付方式
     */
    private String pdrPaymentName;

    /**
     * 第三方支付接口交易号
     */
    private String pdrTradeSn;

    /**
     * 添加时间
     */
    private Integer pdrAddTime;

    /**
     * 支付状态 0未支付1支付
     */
    private Boolean pdrPaymentState;

    /**
     * 支付时间
     */
    private Integer pdrPaymentTime;

    /**
     * 管理员名
     */
    private String pdrAdmin;


    @Override
    protected Serializable pkVal() {
        return this.pdrId;
    }

}
