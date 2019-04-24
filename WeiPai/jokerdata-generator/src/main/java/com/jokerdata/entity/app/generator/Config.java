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
 * @since 2019-04-24
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
    private Integer configId;

    private String configName;

    /**
     * 编码，此参数不在管理后台显示，此参数不为空，后台不能删除该记录
     */
    private String configCode;

    private String configContent;


    @Override
    protected Serializable pkVal() {
        return this.configId;
    }

}
