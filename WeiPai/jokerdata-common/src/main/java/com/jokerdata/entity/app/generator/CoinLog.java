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
@TableName("g_coin_log")
public class CoinLog extends Model<CoinLog> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer log_id;

    private String log_user_id;

    private String log_user_name;

    private String log_admin_id;

    private String log_admin_name;

    /**
     * admin管理员,admin_change后台变动,task_freeze任务发布冻结,task_cancel取消订单解冻预存款,recharge充值,task_income任务收入,task_income_check收入待审核  check_false失败，task_outcome任务支出,task_back任务未完成, sign 签到 ，refound取消退还
     */
    private String log_type;

    /**
     * 可用金额变更0表示未变更
     */
    private BigDecimal log_av_coin;

    /**
     * 冻结金额变更0表示未变更
     */
    private BigDecimal log_freeze_coin;

    /**
     * 备注
     */
    private String log_mark;

    /**
     * 添加时间
     */
    private Integer add_time;


    @Override
    protected Serializable pkVal() {
        return this.log_id;
    }

}
