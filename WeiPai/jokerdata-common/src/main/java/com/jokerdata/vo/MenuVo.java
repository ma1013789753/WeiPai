package com.jokerdata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 15:34
 * @describe：
 * @version: 1.0
 */

@Data
public class MenuVo {

    private Integer tid;

    private Integer father;

    private String icon;

    private String resources;

    private String title;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<MenuVo> children;

    public MenuVo() {
    }

    public MenuVo(Integer tid, Integer father, String icon, String resources, String title) {
        this.tid = tid;
        this.father = father;
        this.icon = icon;
        this.resources = resources;
        this.title = title;
    }

    public MenuVo(Integer tid, Integer father, String icon, String resources, String title, String memo, Date createTime) {
        this.tid = tid;
        this.father = father;
        this.icon = icon;
        this.resources = resources;
        this.title = title;
        this.memo = memo;
        this.createTime = createTime;
    }
}
