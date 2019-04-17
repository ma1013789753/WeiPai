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
    * 预存款变更日志表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-17
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
    private Integer lg_id;

    /**
     * 会员编号
     */
    private Integer lg_member_id;

    /**
     * 会员名称
     */
    private String lg_member_name;

    /**
     * 管理员名称
     */
    private String lg_admin_name;

    /**
     * admin管理员,order_pay下单支付预存款,order_freeze下单冻结预存款,order_cancel取消订单解冻预存款,order_comb_pay下单支付被冻结的预存款,recharge充值,cash_apply申请提现冻结预存款,cash_pay提现成功,cash_del取消提现申请，cash_refuse平台拒绝提现申请， 解冻预存款,refund退款 ，task_in任务收入 task_in_check收入待审   check_false审核失败
     */
    private String lg_type;

    /**
     * 可用金额变更0表示未变更
     */
    private BigDecimal lg_av_amount;

    /**
     * 冻结金额变更0表示未变更
     */
    private BigDecimal lg_freeze_amount;

    /**
     * 添加时间
     */
    private Integer lg_add_time;

    /**
     * 描述
     */
    private String lg_desc;

    /**
     * 分享收入时为sl_id
     */
    private String lg_from_data;


    @Override
    protected Serializable pkVal() {
        return this.lg_id;
    }

}
