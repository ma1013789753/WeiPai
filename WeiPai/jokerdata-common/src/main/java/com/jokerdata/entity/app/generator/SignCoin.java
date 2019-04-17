package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("g_sign_coin")
public class SignCoin extends Model<SignCoin> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "g_id", type = IdType.AUTO)
    private Integer g_id;

    /**
     * 连续签到天数
     */
    private Integer limit_num;

    /**
     * 奖励金币数
     */
    private Integer sign_coin;


    @Override
    protected Serializable pkVal() {
        return this.g_id;
    }

}
