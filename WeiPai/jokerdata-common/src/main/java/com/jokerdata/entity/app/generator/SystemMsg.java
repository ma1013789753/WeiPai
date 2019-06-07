package com.jokerdata.entity.app.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
    * 系统信息
    * </p>
 *
 * @author oldMa
 * @since 2019-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_system_msg")
public class SystemMsg extends Model<SystemMsg> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    /**
     * 通知类型 系统消息 : system , 商机推送 : shop , 互推进行中 : push   互推成功（发起人）:succ_master  互推成功（转发人）：succ_user      互推失败push_false
     */
    private String noticeType;

    /**
     * 通知内容
     */
    private String noticeContent;

    /**
     * 是否是新消息 1已读
     */
    private Integer isNew;

    /**
     * 发布人姓名
     */
    private String authorName;

    /**
     * 通知时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String addTime;

    /**
     * 1积分  2 现金  0 无
     */
    private Integer type;

    /**
     * 对应的数字
     */
    private BigDecimal data;

    /**
     * 默认 0 所有人 , 1 个人 ( user_id=data相互关联 )
     */
    private Integer isAll;

    /**
     * 默认0 通知内容没有base64加密 , 1 通知内容有base64加密
     */
    private Integer isBase64;

    /**
     *接收人名称
     */
    @TableField(exist = false)
    private String userName;

    @Override
    protected Serializable pkVal() {
        return this.noticeId;
    }

}
