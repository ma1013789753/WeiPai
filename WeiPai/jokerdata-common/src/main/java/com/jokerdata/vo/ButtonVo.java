package com.jokerdata.vo;

import lombok.Data;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 15:33
 * @describe：
 * @version: 1.0
 */

@Data
public class ButtonVo {

    private Integer tid;

    private String resources;

    private String title;

    public ButtonVo() {
    }

    public ButtonVo(Integer tid, String resources, String title) {
        this.tid = tid;
        this.resources = resources;
        this.title = title;
    }
}
