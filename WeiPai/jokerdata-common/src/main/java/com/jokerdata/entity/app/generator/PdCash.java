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
    * 预存款提现记录表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_pd_cash")
public class PdCash extends Model<PdCash> {
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "pdc_id", type = IdType.AUTO)
    private Integer pdcId;

    /**
     * 记录唯一标示
     */
    private String pdcSn;

    /**
     * 会员编号
     */
    private Integer pdcMemberId;

    /**
     * 会员名称
     */
    private String pdcMemberName;

    /**
     * 金额
     */
    private BigDecimal pdcAmount;

    /**
     * 收款银行
     */
    private String pdcBankName;

    /**
     * 收款账号
     */
    private String pdcBankNo;

    /**
     * 开户人姓名
     */
    private String pdcBankUser;

    /**
     * 添加时间
     */
    private Integer pdcAddTime;

    /**
     * 付款时间
     */
    private Integer pdcPaymentTime;

    /**
     * 提现支付状态 0默认1支付完成 2拒绝
     */
    private Boolean pdcPaymentState;

    /**
     * 支付管理员
     */
    private String pdcPaymentAdmin;

    /**
     * 拒绝提现原因
     */
    private String refuseReason;


    @Override
    protected Serializable pkVal() {
        return this.pdcId;
    }

}
