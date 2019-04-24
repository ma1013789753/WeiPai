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
 * @since 2019-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_sign")
public class Sign extends Model<Sign> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sign_id", type = IdType.AUTO)
    private Integer signId;

    private Integer userId;

    /**
     * 奖励金额
     */
    private BigDecimal signCoin;

    /**
     * 签到日期
     */
    private String signDate;

    private Boolean signState;

    /**
     * 添加时间
     */
    private String addTime;


    @Override
    protected Serializable pkVal() {
        return this.signId;
    }

}
