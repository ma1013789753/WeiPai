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
 * @since 2019-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_feedback")
public class Feedback extends Model<Feedback> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "feedback_id", type = IdType.AUTO)
    private Integer feedbackId;

    private Integer userId;

    private String userName;

    /**
     * 是否已读 1已读
     */
    private Integer isRead;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 电子邮箱
     */
    private String userMail;

    /**
     * 添加时间
     */
    private Long addTime;


    @Override
    protected Serializable pkVal() {
        return this.feedbackId;
    }

}
