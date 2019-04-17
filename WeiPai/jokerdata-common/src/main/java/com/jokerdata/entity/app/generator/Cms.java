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
@TableName("g_cms")
public class Cms extends Model<Cms> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "cms_id", type = IdType.AUTO)
    private Integer cms_id;

    /**
     * 标题
     */
    private String cms_title;

    /**
     * 作者
     */
    private String cms_author;

    /**
     * 分类id
     */
    private Integer cate_id;

    /**
     * 分类名
     */
    private String cate_name;

    /**
     * 封面图
     */
    private String cms_image;

    /**
     * 内容
     */
    private String cms_content;

    /**
     * 排序
     */
    private Integer cms_sort;

    /**
     * 添加时间
     */
    private String add_time;

    private Integer see_num;


    @Override
    protected Serializable pkVal() {
        return this.cms_id;
    }

}
