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
    * 验证码表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-17
 */
@Data
@TableName("g_sms")
public class Sms extends Model<Sms> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sms_id", type = IdType.AUTO)
    private Integer sms_id;

    private Integer smsId;
    /**
     * 验证号码
     */
    private String sms_mobi;

    /**
     * 验证码内容
     */
    private String sms_content;

    /**
     * 验证类型,1注册，2忘记密码,3短信登录
     */
    private int sms_type;

    /**
     * 发送时间
     */
    private String add_time;


    @Override
    protected Serializable pkVal() {
        return this.sms_id;
    }

}
