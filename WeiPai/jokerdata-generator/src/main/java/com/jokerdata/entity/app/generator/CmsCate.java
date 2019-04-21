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
@TableName("g_cms_cate")
public class CmsCate extends Model<CmsCate> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "cate_id", type = IdType.AUTO)
    private Integer cateId;

    /**
     * 分类名
     */
    private String cateName;

    private Integer cateSort;

    /**
     * 默认0，1禁用
     */
    private Boolean cateState;


    @Override
    protected Serializable pkVal() {
        return this.cateId;
    }

}
