/**
  * Copyright 2019 bejson.com 
  */
package com.jokerdata.jokerdatapassageway.common.entity;
import java.util.Date;

/**
 * Auto-generated: 2019-06-08 19:24:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private String ip;
    private int port;
    private Date expire_time;
    private String city;
    public void setIp(String ip) {
         this.ip = ip;
     }
     public String getIp() {
         return ip;
     }

    public void setPort(int port) {
         this.port = port;
     }
     public int getPort() {
         return port;
     }

    public void setExpire_time(Date expire_time) {
         this.expire_time = expire_time;
     }
     public Date getExpire_time() {
         return expire_time;
     }

    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

}