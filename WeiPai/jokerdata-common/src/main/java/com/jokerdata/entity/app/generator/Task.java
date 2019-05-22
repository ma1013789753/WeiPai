package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("g_task")
public class Task extends Model<Task> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 奖金
     */
    private BigDecimal award;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件
     */
    private String link;

    /**
     * 1删除
     */
    private Boolean isDel;

    /**
     * 1开始 2完成
     */
    private Boolean state;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date modifyTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
