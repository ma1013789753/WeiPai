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
 * @since 2019-04-17
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
    private Integer pdc_id;

    /**
     * 记录唯一标示
     */
    private String pdc_sn;

    /**
     * 会员编号
     */
    private Integer pdc_member_id;

    /**
     * 会员名称
     */
    private String pdc_member_name;

    /**
     * 金额
     */
    private BigDecimal pdc_amount;

    /**
     * 收款银行
     */
    private String pdc_bank_name;

    /**
     * 收款账号
     */
    private String pdc_bank_no;

    /**
     * 开户人姓名
     */
    private String pdc_bank_user;

    /**
     * 添加时间
     */
    private Integer pdc_add_time;

    /**
     * 付款时间
     */
    private Integer pdc_payment_time;

    /**
     * 提现支付状态 0默认1支付完成 2拒绝
     */
    private Boolean pdc_payment_state;

    /**
     * 支付管理员
     */
    private String pdc_payment_admin;

    /**
     * 拒绝提现原因
     */
    private String refuse_reason;


    @Override
    protected Serializable pkVal() {
        return this.pdc_id;
    }

}
