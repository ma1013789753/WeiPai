/**
  * Copyright 2019 bejson.com 
  */
package com.jokerdata.parames.vo;

import com.jokerdata.entity.app.generator.User;
import lombok.Data;

/**
 * Auto-generated: 2019-04-17 21:17:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class UserInfo extends User {


    private String userAvatar;
    private String token;


    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}