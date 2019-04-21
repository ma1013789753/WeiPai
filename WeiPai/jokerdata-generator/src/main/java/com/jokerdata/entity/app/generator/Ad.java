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
@TableName("g_ad")
public class Ad extends Model<Ad> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ad_id", type = IdType.AUTO)
    private Integer adId;

    /**
     * 显示位置
     */
    private String adType;

    /**
     * 封面
     */
    private String adImg;

    /**
     * 1 禁用
     */
    private Boolean adState;

    /**
     * 广告内容id等
     */
    private String adDataId;

    /**
     * 广告内容类型 默认cms资讯
     */
    private String adDataType;

    private String addTime;

    private Integer adSort;


    @Override
    protected Serializable pkVal() {
        return this.adId;
    }

}
