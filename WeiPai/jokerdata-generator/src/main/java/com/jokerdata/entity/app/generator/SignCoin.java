package com.jokerdata.entity.app.generator;

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
@TableName("g_sign_coin")
public class SignCoin extends Model<SignCoin> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "g_id", type = IdType.AUTO)
    private Integer gId;

    /**
     * 连续签到天数
     */
    private Integer limitNum;

    /**
     * 奖励金币数
     */
    private Integer signCoin;


    @Override
    protected Serializable pkVal() {
        return this.gId;
    }

}
