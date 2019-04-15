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
    * 预存款变更日志表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_pd_log")
public class PdLog extends Model<PdLog> {
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "lg_id", type = IdType.AUTO)
    private Integer lgId;

    /**
     * 会员编号
     */
    private Integer lgMemberId;

    /**
     * 会员名称
     */
    private String lgMemberName;

    /**
     * 管理员名称
     */
    private String lgAdminName;

    /**
     * admin管理员,order_pay下单支付预存款,order_freeze下单冻结预存款,order_cancel取消订单解冻预存款,order_comb_pay下单支付被冻结的预存款,recharge充值,cash_apply申请提现冻结预存款,cash_pay提现成功,cash_del取消提现申请，cash_refuse平台拒绝提现申请， 解冻预存款,refund退款 ，task_in任务收入 task_in_check收入待审   check_false审核失败
     */
    private String lgType;

    /**
     * 可用金额变更0表示未变更
     */
    private BigDecimal lgAvAmount;

    /**
     * 冻结金额变更0表示未变更
     */
    private BigDecimal lgFreezeAmount;

    /**
     * 添加时间
     */
    private Integer lgAddTime;

    /**
     * 描述
     */
    private String lgDesc;

    /**
     * 分享收入时为sl_id
     */
    private String lgFromData;


    @Override
    protected Serializable pkVal() {
        return this.lgId;
    }

}
