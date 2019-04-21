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
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_auth_rule")
public class AuthRule extends Model<AuthRule> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限规则
     */
    private String name;

    /**
     * 权限名称
     */
    private String title;

    private Integer pid;

    private Boolean type;

    private String condition;

    /**
     * 是否菜单
     */
    private Boolean isMenu;

    /**
     * 图标
     */
    private String icon;

    private Integer sort;

    private Boolean status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
