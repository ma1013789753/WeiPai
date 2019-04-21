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
    * 
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_coin_log")
public class CoinLog extends Model<CoinLog> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    private String logUserId;

    private String logUserName;

    private String logAdminId;

    private String logAdminName;

    /**
     * admin管理员,admin_change后台变动,task_freeze任务发布冻结,task_cancel取消订单解冻预存款,recharge充值,task_income任务收入,task_income_check收入待审核  check_false失败，task_outcome任务支出,task_back任务未完成, sign 签到 ，refound取消退还
     */
    private String logType;

    /**
     * 可用金额变更0表示未变更
     */
    private BigDecimal logAvCoin;

    /**
     * 冻结金额变更0表示未变更
     */
    private BigDecimal logFreezeCoin;

    /**
     * 备注
     */
    private String logMark;

    /**
     * 添加时间
     */
    private Integer addTime;


    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

}
