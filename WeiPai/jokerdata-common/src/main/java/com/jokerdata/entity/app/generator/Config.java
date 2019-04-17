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
@TableName("g_config")
public class Config extends Model<Config> {
    private static final long serialVersionUID = 1L;

    /**
     * 配置项主键
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    private Integer config_id;

    private String config_name;

    /**
     * 编码，此参数不在管理后台显示，此参数不为空，后台不能删除该记录
     */
    private String config_code;

    private String config_content;


    @Override
    protected Serializable pkVal() {
        return this.config_id;
    }

}
