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
    * 公众号标签
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_gzh_tag")
public class GzhTag extends Model<GzhTag> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;

    /**
     * 公众号标签名称
     */
    private String tagName;

    /**
     * 大数在前  小数在后
     */
    private Integer tagSort;


    @Override
    protected Serializable pkVal() {
        return this.tagId;
    }

}
