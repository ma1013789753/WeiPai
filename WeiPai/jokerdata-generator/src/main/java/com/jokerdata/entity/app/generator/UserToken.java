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
    * 
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_user_token")
public class UserToken extends Model<UserToken> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ut_id", type = IdType.AUTO)
    private Integer utId;

    private Integer userId;

    private String userName;

    private String userToken;

    private String addTime;


    @Override
    protected Serializable pkVal() {
        return this.utId;
    }

}
