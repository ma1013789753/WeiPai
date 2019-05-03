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
 * @since 2019-04-19
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

    private String signState;

    /**
     * 添加时间
     */
    private String addTime;


    @Override
    protected Serializable pkVal() {
        return this.signId;
    }

}
