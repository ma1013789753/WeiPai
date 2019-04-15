package com.jokerdata.entity.app.generator;

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
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_auth_group_access")
public class AuthGroupAccess extends Model<AuthGroupAccess> {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员ID
     */
    private Integer uid;

    /**
     * 用户组ID
     */
    private Integer groupId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
