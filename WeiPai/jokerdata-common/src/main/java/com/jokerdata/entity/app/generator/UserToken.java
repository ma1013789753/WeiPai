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
@TableName("g_user_token")
public class UserToken extends Model<UserToken> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ut_id", type = IdType.AUTO)
    private Integer ut_id;

    private Integer user_id;

    private String user_name;

    private String user_token;

    private String add_time;


    @Override
    protected Serializable pkVal() {
        return this.ut_id;
    }

}
