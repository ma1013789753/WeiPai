package com.jokerdata.common.push.config;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MessagePush {
    /**
     * 主键
     */
    private String id;

    /**
     * 消息分类
     */
    private String type;

    /**
     *消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 推送时间
     */
    private Date pushTime;

    /**
     * 结束时间
     */
    private Date endTime;

   private List<String> tags;
}
