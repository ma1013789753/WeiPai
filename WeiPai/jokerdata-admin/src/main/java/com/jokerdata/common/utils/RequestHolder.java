package com.jokerdata.common.utils;

import com.jokerdata.entity.app.generator.User;

public class RequestHolder {
 
    private final static ThreadLocal<User> requestHolder = new ThreadLocal<>();
    public static void add(User user) {
        requestHolder.set(user);
    }
 
    public static User getUser() {
        return requestHolder.get();
    }
 
    public static void remove() {
        requestHolder.remove();
    }
}