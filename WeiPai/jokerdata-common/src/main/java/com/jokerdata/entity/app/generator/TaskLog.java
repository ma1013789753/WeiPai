package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author oldMa
 * @since 2019-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_task_log")
public class TaskLog extends Model<TaskLog> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * task_id
     */
    private Long tId;

    /**
     * 现金奖励LogId
     */
    private Long pId;

    /**
     * account_id
     */
    private Long accountId;

    /**
     * 反馈链接
     */
    private String link;

    private Date createTime;

    private Date modifyTime;

    /**
     * 0开始1完成2取消
     */
    private Boolean state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
