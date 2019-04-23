package com.jokerdata.common;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClassUtil {

    public static String URL = "http://youdianshare.com/";

    public static <T2> T2 beanToBean( Object beanObj,Class<T2> class2){

        Map<String,Object> map = beanToMap(beanObj);

        return mapToBean(map,class2);

    }

    /**
     * 使用Introspector，map集合成javabean
     *
     * @param map       map
     * @param beanClass bean的Class类
     * @return bean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {


        try {
            T t = beanClass.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(t, map.get(property.getName()));
                }
            }
            return t;
        } catch (Exception ex) {
            throw new RuntimeException();
        }

    }

    /**
     * 使用Introspector，对象转换为map集合
     *
     * @param beanObj javabean对象
     * @return map集合
     */
    public static Map<String, Object> beanToMap(Object beanObj) {

        if (null == beanObj) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo
                    beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(beanObj) : null;
                map.put(key, value);
            }

            return map;
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    //单个bean
    public static Map<String, Object> toLowBean(Object beanObj) {

        Map<String, Object> map = beanToMap(beanObj);
        Map<String, Object> map2 = new HashMap<>();
        map.keySet().forEach(s -> {
            String key = s;

            Object value = map.get(key);
            for (int i = 0; i < s.length(); i++) {
                char index = s.charAt(i);
                if(Character.isUpperCase(index)){
                    s = s.replace(index+"","_"+Character.toLowerCase(index));
                }
            }
            map2.put(s,value);
        });



        return map2;
    }

    //单个bean
    public static <T> List<Map<String,Object>> toLowBeanList(List<T> beanObjs) {
        List<Object> list = new ArrayList<>();
        beanObjs.forEach(bean->{
            list.add(bean);
        });
        return toLowBean2(list);
    }

    //单个bean
    public static List<Map<String,Object>> toLowBean2(List<Object> beanObjs) {
        List<Map<String,Object>> data = new ArrayList<>();

        for (int j = 0; j < beanObjs.size(); j++) {
            Object beanObj = beanObjs.get(j);
            Map<String, Object> map = beanToMap(beanObj);
            Map<String, Object> map2 = new HashMap<>();
            map.keySet().forEach(s -> {
                String key = s;

                Object value = map.get(key);
                for (int i = 0; i < s.length(); i++) {
                    char index = s.charAt(i);
                    if(Character.isUpperCase(index)){
                        s = s.replace(index+"","_"+Character.toLowerCase(index));
                    }
                }
                map2.put(s,value);
            });
            data.add(map2);
        }
        return data;
    }

    //获取头像
    public static String getAvatar(String userId){

        String str = "http://youdianshare.com/Upload/avatar/"+userId+".png";

        HttpURLConnection urlcon2 = null;
        try {
            URL url = new URL(str);
            urlcon2 = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long TotalSize=Long.parseLong(urlcon2.getHeaderField("Content-Length"));
        if (TotalSize>0){
            return str;
        }
        return "http://youdianshare.com/Upload/avatar/default.png";

    }

    //获取头像
    public static String getTaxt(String timeTamp){
        Long time = Long.parseLong(timeTamp)*1000;
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  sdf.format(date);


    }

    //获取头像
    public static String getTaxtLater(String timeTamp){
        Long time = Long.parseLong(timeTamp)*1000;
        Date date = new Date(time);

        return RelativeDateFormat.format(date);
    }


}
