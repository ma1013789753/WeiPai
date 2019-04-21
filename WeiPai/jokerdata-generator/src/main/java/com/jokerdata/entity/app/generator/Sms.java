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
    * 验证码表
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_sms")
public class Sms extends Model<Sms> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sms_id", type = IdType.AUTO)
    private Integer smsId;

    /**
     * 验证号码
     */
    private String smsMobi;

    /**
     * 验证码内容
     */
    private String smsContent;

    /**
     * 验证类型,1注册，2忘记密码,3短信登录
     */
    private Boolean smsType;

    /**
     * 发送时间
     */
    private String addTime;


    @Override
    protected Serializable pkVal() {
        return this.smsId;
    }

}
