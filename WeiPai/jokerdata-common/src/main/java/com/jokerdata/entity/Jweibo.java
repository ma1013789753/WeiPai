package com.jokerdata.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class Jweibo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微博ID
     */
    private String id;

    /**
     * 博主ID
     */
    private Long userId;

    /**
     * 博主昵称
     */
    private String screenName;

    /**
     * 博主头像
     */
    private String profileImage;

    /**
     * 发博时间
     */
    private String createdAt;

    /**
     * 发博设备
     */
    private String source;

    /**
     * 微博内容
     */
    private String text;

    /**
     * 内容图片列表
     */
    private List<String> images = null;

    /**
     * 内容视频列表
     */
    private List<String> video = null;

    /**
     * 转发微博列表
     */
    private Jweibo retweeted ;

}
