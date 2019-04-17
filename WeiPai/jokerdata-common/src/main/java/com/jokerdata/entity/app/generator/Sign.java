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
@TableName("g_sign")
public class Sign extends Model<Sign> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sign_id", type = IdType.AUTO)
    private Integer sign_id;

    private Integer user_id;

    /**
     * 奖励金额
     */
    private BigDecimal sign_coin;

    /**
     * 签到日期
     */
    private String sign_date;

    private Boolean sign_state;

    /**
     * 添加时间
     */
    private String add_time;


    @Override
    protected Serializable pkVal() {
        return this.sign_id;
    }

}
