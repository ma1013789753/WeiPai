package com.jokerdata.entity.admin.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 互推分享表
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_share_log")
public class TshareLog extends Model<TshareLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String shareUrl;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 用户id
     */
    private String shareId;

    /**
     * 用户id
     */
    private String forwardContent;

    /**
     * 第三方标识
     */
    private String platform;

    /**
     * 第三方平台
     */
    private String shareStatus;

    /**
     * 用户id
     */
    private Double income;

    /**
     * 用户id
     */
    private String incomeType;

    /**
     * 用户id
     */
    private String incomeStatus;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
