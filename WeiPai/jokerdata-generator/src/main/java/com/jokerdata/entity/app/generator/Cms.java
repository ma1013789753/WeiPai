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
@TableName("g_cms")
public class Cms extends Model<Cms> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "cms_id", type = IdType.AUTO)
    private Integer cmsId;

    /**
     * 标题
     */
    private String cmsTitle;

    /**
     * 作者
     */
    private String cmsAuthor;

    /**
     * 分类id
     */
    private Integer cateId;

    /**
     * 分类名
     */
    private String cateName;

    /**
     * 封面图
     */
    private String cmsImage;

    /**
     * 内容
     */
    private String cmsContent;

    /**
     * 排序
     */
    private Integer cmsSort;

    /**
     * 添加时间
     */
    private String addTime;

    private Integer seeNum;


    @Override
    protected Serializable pkVal() {
        return this.cmsId;
    }

}
