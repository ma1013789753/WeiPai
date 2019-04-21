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
    * 奖励预备
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_math")
public class Math extends Model<Math> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "math_id", type = IdType.AUTO)
    private Integer mathId;

    /**
     * 分享编号
     */
    private Integer shareId;

    /**
     * 次序
     */
    private Integer sotr;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 0 有效   1 无效
     */
    private Integer state;

    /**
     * 对应的分享logid
     */
    private Integer logId;


    @Override
    protected Serializable pkVal() {
        return this.mathId;
    }

}
