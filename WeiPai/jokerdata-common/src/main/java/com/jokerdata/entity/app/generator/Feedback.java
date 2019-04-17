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
    * 意见反馈
    * </p>
 *
 * @author oldMa
 * @since 2019-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_feedback")
public class Feedback extends Model<Feedback> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "feedback_id", type = IdType.AUTO)
    private Integer feedback_id;

    private Integer user_id;

    private String user_name;

    /**
     * 是否已读 1已读
     */
    private Integer is_read;

    /**
     * 反馈内容
     */
    private String feedback_content;

    /**
     * 电子邮箱
     */
    private String user_mail;

    /**
     * 添加时间
     */
    private String add_time;


    @Override
    protected Serializable pkVal() {
        return this.feedback_id;
    }

}
