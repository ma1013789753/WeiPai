package com.jokerdata.common.config.push.config;

import java.util.Date;

public class MessagePush {
    /**
     * 主键
     */
    private String id;

    /**
     * 消息分类
     */
    private int type;

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

    /**
     * 删除标志 0:否 1:是
     */
    private byte delFlag;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建用户ID
     */
    private String createUser;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改用户ID
     */
    private String updateUser;

    private Integer sendFlag;

    /**
     * 用户分群的Id
     */
    private String segment;

    /**
     * 机型id
     */
    private String[] registrationIds;

    public String[] getRegistrationIds() {
        return registrationIds;
    }

    public void setRegistrationIds(String[] registrationIds) {
        this.registrationIds = registrationIds;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }
}
