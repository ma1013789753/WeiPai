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
@TableName("g_ad")
public class Ad extends Model<Ad> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ad_id", type = IdType.AUTO)
    private Integer ad_id;

    /**
     * 显示位置
     */
    private String ad_type;

    /**
     * 封面
     */
    private String ad_img;

    /**
     * 1 禁用
     */
    private Boolean ad_state;

    /**
     * 广告内容id等
     */
    private String ad_data_id;

    /**
     * 广告内容类型 默认cms资讯
     */
    private String ad_data_type;

    private String add_time;

    private Integer ad_sort;


    @Override
    protected Serializable pkVal() {
        return this.ad_id;
    }

}
