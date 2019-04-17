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
@TableName("g_transmit")
public class Transmit extends Model<Transmit> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "transmit_id", type = IdType.AUTO)
    private Integer transmit_id;


    @Override
    protected Serializable pkVal() {
        return this.transmit_id;
    }

}
