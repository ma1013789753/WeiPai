package com.jokerdata.common;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.swing.text.html.HTML;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShareUtil {

    public static String URL = "http://youdianshare.com/";

    /**
     * bean 转bean
     * @param beanObj
     * @param class2
     * @param <T2>
     * @return
     */
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

    //获取时间
    public static String getTaxt(Object timeTamp){
        if(timeTamp == null){
            return "1980-08-08 8:08";
        }
        if(StringUtils.isEmpty(timeTamp)){
            return "1980-08-08 8:08";
        }
        Long time = Long.parseLong(timeTamp.toString())*1000;
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  sdf.format(date);


    }

    //获取多久以前
    public static String getTaxtLater(String timeTamp){
        Long time = Long.parseLong(timeTamp)*1000;
        Date date = new Date(time);

        return RelativeDateFormat.format(date);
    }

    //数据解密
    public static String Base64Decode(String str) {
        if(StringUtils.isEmpty(str)){
            return "";
        }
        String data = null;
        try {
            data = new String(Base64Utils.decode(str.getBytes()),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }

    //数据加密
    public static String Base64Encode(String str){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        return MD5.MD5Encode(str,"utf-8");
    }


    public static boolean isExit(Object object){
        if(object == null){
            return false;
        }
        if(StringUtils.isEmpty(object.toString())){
            return false;
        }
        return true;
    }

    /**
     * 获取图片
     */
    public static String getPic(Object pic){
        if(StringUtils.isEmpty(pic)){
            return "";
        }
        if(pic.toString().indexOf(".")==0){
            pic = pic.toString().substring(1);
        }
        return URL + pic;
    }



    public static String htmlEncode(String source) {

        if(source == null) {
            return "";
        }


        return StringEscapeUtils.unescapeHtml4(source);
    }

    public static String get_img_thumb_url2(String html){
        String pregRule = "/<[img|IMG].*?src=[\\'|\"](?!http)(.*?(?:[\\.jpg|\\.jpeg|\\.png|\\.gif|\\.bmp]))[\\'|\"](.*?[\\/]?>)/";
        html = html.replaceAll(pregRule,"<img src="+URL+"${1}\2");
        return  html;
    }

    public static void main(String[] args) {
        String str = "&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;本协议由您与北京微爱传承文化发展有限公司就有点分享&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;APP&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（以下简称&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;“&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;”&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;）使用相关事宜而签订。本协议具有合同效力，在您注册成为有点分享用户时，请认真阅读本协议，尤其是限制用户权利、免除有点分享义务的条款，并选择接受或不接受。您的注册成功及使用行为即表示您已充分阅读、理解并接受本协议的全部内容，并自愿接受本协议各项条款之约束，成为有点分享用户。有点分享有权随时修订本协议并予公告，如您不同意相关修订，应立即停止使用有点分享；如您继续使用有点分享，则表示您已接受并自愿遵守修订后的本协议。本协议内容包括协议正文及有点分享不时发布的各类规则，规则文本为本协议不可分割的部分，与协议正文具有同等法律效力。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第一条用户资格&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;1.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;本协议所称&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;“&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;”&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;，特指符合本协议所约定的用户条件、同意遵守本协议内容并使用有点分享的自然人或单位。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;1.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;只有符合下列条件之一的自然人或单位才能申请成为有点分享用户、使用有点分享的服务：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）年满十八周岁并具有完全民事权利能力和民事行为能力的自然人；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）未满十八周岁但经其监护人同意的自然人；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）依据中华人民共和国法律或注册地法律成立并合法存续的公司、企事业单位、社团组织和其他组织。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;1.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;无民事行为能力人以及无经营或特定经营资格的单位不得注册为有点分享用户，其与有点分享之间的用户协议自始无效，且一经发现，有点分享有权立即终止对用户的服务，并追究其法律责任。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第二条用户的权利和义务&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户有权根据本协议之约定以及有点分享发布的相关规则享受有点分享提供的服务，包括但不限于利用有点分享平台发布任务、领取任务、享受有点分享提供的其他信息服务等。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户有义务确保其所有注册信息真实准确，包括但不限于真实姓名&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;/&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;名称、身份证号&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;/&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;注册号&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;/&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;统一社会信用代码号、联系电话、住所地、电子邮箱等，并保证有点分享可以通过上述联系方式随时与其取得联系；用户有义务在相关信息变更时及时更新注册信息。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户不得以任何形式擅自转让或许可他人使用自己的有点分享账号。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.4 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户应妥善保管自己的用户账号和密码，并对该账号所发生一切操作行为负责；因用户原因（包括但不限于：不按使用规则操作、未及时进行任务提交或确认操作、遗忘或泄漏密码、密码被他人破解、用户的手机或计算机被他人侵入或侵占）导致的任何损失均由用户自行承担。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.5 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户有义务确保其在有点分享平台发布的任务内容真实、准确、无误导性；用户应保证其发布的任务内容所对应的商品和服务是真实、有效、无害且合法的。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.6 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户有义务确保其在有点分享平台发布的任务内容不违反法律法规的限制性、禁止性规定，不侵犯他人知识产权或其他合法权益，不违背社会公共利益或公共道德。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.7 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户在使用有点分享发布、领取任务的过程中应当遵守诚实信用原则，不得存在不正当竞争行为，不得扰乱有点分享平台的正常秩序。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.8 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户承诺自己在有点分享网上交易平台实施的所有行为遵守国家法律、法规和有点分享的相关规定以及各种社会公共利益或公共道德。用户应对以该用户账号进行的所有行为及交易活动负全部责任。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.9 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户不应采取不正当手段（包括但不限于虚假需求等方式）提高自身或他人信用度，或采用不正当手段恶意评价其他用户，降低其他用户信用度。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.10 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户在使用有点分享时不得违反《银行卡业务管理办法》使用银行卡，或利用信用卡套取现金（以下简称&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;“&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;套现&lt;/span&gt;&lt;span style=&quot;font-size: 14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;”&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;）。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.11 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户在有点分享发布、领取任务的过程中与其他用户产生纠纷的，可以请求有点分享从中协调处理；用户如发现其他用户有违法或违反本协议的行为，可以向有点分享举报。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.12 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;除非另有约定，否则用户应自行承担因享受有点分享所提供的自助发布任务、完成任务等服务所产生的相关服务费用，并依法纳税。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.13 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户不得使用以下方式登录有点分享或破坏有点分享所提供的服务：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）以外挂程序或其它任何非正常的技术方式访问或登录有点分享；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）通过任何方式对有点分享内部结构造成或可能造成不合理或不合比例的重大负荷；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）通过任何方式干扰或试图干扰有点分享的正常工作或平台上进行的任何活动。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;2.14&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户同意接收来自有点分享的信息，包括但不限于活动信息、交易信息、促销信息等。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第三条有点分享的权利和义务&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享是一个让存在社交资源供求关系的用户互相发现、互利共赢的基于移动互联网的服务平台，在正常情况下不会介入用户在平台发布、领取任务的活动。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享将在现有技术水平的基础上努力确保整平台的正常运行，尽力避免服务中断或将中断时间限制在可行的最短时间内，保证用户发布、领取任务的顺利进行。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享将对用户在注册使用有点分享过程中所遇到的问题与反馈及时作出回复。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.4 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享有权对用户的注册信息进行审查，在对相关注册信息的真实性、合法性存在合理怀疑时，有点分享有权要求用户做出解释、改正，或不通知用户直接做出停止服务、删除信息、删除账号等处理。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.5 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户因在有点分享网上交易而与其他用户产生纠纷的，用户通过司法部门或行政部门依照法定程序要求有点分享提供相关资料，有点分享将积极配合并提供有关资料；若用户请求有点分享从中协调，或有点分享知悉纠纷情况的，经审核后，有点分享有权通过电子邮件及电话联系向纠纷双方了解纠纷情况，并将所了解的情况通过电子邮件通知纠纷双方。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.6 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;因网上信息平台的特殊性，有点分享难以对所有用户的发布、领取任务的行为以及与之相关的其他事项进行事前审查，但若发生如下情形，有点分享有权无需征得用户同意而直接作出限制用户权利、删除相关信息、发出警告通知、停止向该用户提供服务等处理：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）用户以非自然人名义进行认证之后认证主体自行注销或者经有权机关吊销营业执照、登记证书或撤销的；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）根据用户所发布、领取任务的性质，在任务内容、领取及完成方式以及其他方面存在违反本协议或现行法律法规的情形；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）其他用户或第三方通知有点分享，认为某个用户或具体发布、领取任务的过程中存在违法或不当行为并提供相关证据，而有点分享无法联系到该用户核实或验证该用户向有点分享提供的任何资料；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（四）其他用户或第三方通知有点分享，认为某个用户或具体发布、领取任务的过程中存在违法或不当行为并提供相关证据，而有点分享以普通非专业人员的认知水平为标准对相关内容进行甄别，可以基本认定这些内容或行为可能对其他用户或第三方或有点分享自身造成损害或因之承担法律责任。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.7 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;根据国家法律、法规、行政规章规定以及本协议约定，结合有点分享所掌握的事实，可以认定该用户存在违法或违反本协议约定之行为以及在有点分享平台上的其他不当行为，则有点分享有权无需征得用户的同意而直接在有点分享平台及其网站上以公告形式公布该用户的违法行为，并有权随时作出删除相关信息、停止服务提供等处理。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.8 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享依据本协议及相关规则，可以对用户缴存或领取的存放于有点分享账户内的资金进行冻结、划扣、先行赔付、退款等处置；因违规而被封号的用户无权处分相应账户中的资金。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;3.9 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享有权在不通知用户的前提下，删除或采取其他限制性措施处理包括但不限于下列信息：以规避费用为目的、以炒作信用为目的、存在欺诈等恶意或虚假内&lt;/span&gt; &lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;容、与社交资源供求无关或不是以之为目的、存在恶意竞价或其他试图扰乱正常市场秩序因素、违反公共利益或可能严重损害有点分享和其他用户合法权益的。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第四条服务的中断和停止&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;4.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;在有点分享可自行决定以合理理由（包括但不限于有点分享认为用户已违反本协议，或用户超过一定期限未登录有点分享等情形）停止对用户的服务，并在五年内保存用户在有点分享的全部资料（包括但不限于用户信息、商品信息、发布与领取任务信息等），而无需通知该用户；同时，有点分享可自行决定在先行通知的情况下，随时停止提供全部或部分服务。服务停止后，有点分享没有义务为用户保留原用户资料或与之相关的任何信息，或转发任何未曾阅读或发送的信息给用户或第三方。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;4.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;如用户向有点分享提出注销用户身份，需经有点分享审核同意，由有点分享注销该注册用户，方可解除本协议，但有点分享仍保留下列权利：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）用户注销后，有点分享有权保留该用户的资料，包括但不限于用户注册信息、发布、领取任务信息等；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）如用户注销前在有点分享平台上存在违反本协议或违法行为，有点分享仍可行使本协议所约定的权利。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;4.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;如存在下列情形，有点分享可以通过注销用户的方式停止服务：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）用户在被有点分享停止提供服务后，再次直接或间接或以他人名义注册为有点分享用户的，有点分享有权再次单方停止为该用户提供服务；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）有点分享发现用户注册信息中主要内容是虚假的；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）本协议更新时，用户未确认新的协议的；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（四）其它有点分享认为需停止服务的情况。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第五条免责声明&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;当用户接受本协议时，用户应明确了解并同意：有点分享依据现有技术提供服务，尽管有点分享将尽合理努力维护平台的正常运行，但有点分享无法随时预见到任何技术上的问题或其他困难，该等困难可能导致用户受到影响（包括但不限于领取、发布任务延迟、操作失败等），请用户谨慎考虑有点分享所提供服务可能带来的风险，用户使用有点分享之风险由用户自行承担。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享不保证以下事项：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）有点分享符合用户的全部要求；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）有点分享在任何情况下均能够不受干扰地及时提供服务、安全可靠或免于出现故障或错误；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）有点分享平台服务所依据的数据来源和取得结果是正确或可靠的。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;是否经由有点分享下载或取得任何资料，由用户自行考虑、衡量并且自负风险，因下载任何资料而导致用户电脑系统的任何损坏或资料流失，由用户自行负责。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.4 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户经由有点分享取得的建议和资讯，无论其形式或表现，绝不构成本协议未明示规定的任何保证。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.5 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;基于以下原因而造成用户的利润、商誉损失或其它无形损失，有点分享不承担任何直接或间接赔偿责任：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）有点分享的使用或无法使用；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）用户的资料遭到未获授权的存取或变更；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）有点分享中任何第三方之声明或行为；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（四）用户在有点分享中发布和领取任务的行为及其后果；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（五）有点分享其它相关事宜。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.6 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享是平等地为所有用户提交社交资源推广、变现信息的服务平台，对于用户所发布任务的合法性、真实性及拟推广产品的信用和品质等，有点分享无事先审查义务且不承担任何连带责任。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;5.7 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享提供与其它网站或&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;APP&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;的链接，用户可能会因此连结至其它运营商经营的网站或&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;APP&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;，但不表示有点分享对上述链接网站、&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;APP&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;及其内容进行管理监督。因使用或依赖任何此类网站或&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;APP&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;发布的或经由此类网站或&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;APP&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;获得的任何内容、产品或服务所产生的任何损害或损失，有点分享不承担任何直接或间接赔偿责任。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第六条知识产权&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;6.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;所有出现在有点分享上的内容，包括但不限于作品、图片、档案、资料、网站构架、网站版面的安排、网页设计、软件、经由有点分享呈现的推广信息或资讯，均由有点分享或相关权利人依法享有相应知识产权（包括但不限于著作权、商标权、专利权或其它专属权利等），用户仅在符合使用目的的前提下被许可浏览和使用相关内容。未经有点分享或相关权利人明示授权，用户不得复制、修改、出租、出借、出售、传送、删除或以任何不符合使用目的的方式使用前述内容信息。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;6.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享授予用户不可转移及非专属的使用权，使用户可以通过计算机、手机等移动终端使用有点分享的目标代码（以下简称&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;“&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;软件&lt;/span&gt;&lt;span style=&quot;font-size: 14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;”&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;），但用户不得自行或许可任何第三方，复制、修改、创作衍生作品、进行还原工程、反向组译，或以其它方式破译或试图破译源代码，或出售、转让软件或对软件进行再授权，或以其它方式移转软件之任何权利。用户同意不以任何方式修改软件，或使用修改后的软件。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;6.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户不得经由非有点分享所提供的界面使用有点分享所提供的服务。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第七条保密&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;7.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户保证在使用有点分享过程中所获悉的属于有点分享及其他用户或第三方的且无法自公开渠道获得的文件及资料（包括但不限于商业秘密、公司计划、运营活动、财务信息、技术信息、经营信息及其他商业秘密）予以保密。未经该资料和文件的原始提供方同意，用户不得向其他用户或第三方泄露该信息的全部或者部分内容。但法律、法规、行政规章另有规定或者双方另有约定的除外。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;7.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享不会向任何人出售或出借用户的注册信息，除非事先得到用户得许可；有点分享亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播用户的注册信息，任何用户如从事上述活动，一经发现，有点分享有权立即停止对该用户的服务或注销其账号。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;7.3&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;用户的注册信息将在下述情况下部分或全部被披露：&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（一）经用户同意，向第三方披露；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（二）用户在使用有点分享过程中涉及到知识产权类纠纷，有他方主张权利的，有点分享在审核主张方提交的资料后认为披露用户信息有助于纠纷解决的；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（三）根据法律的有关规定，或者行政、司法机关的要求，向第三方或者行政、司法机关披露；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（四）用户出现违反法律或者其他规定、约定的情形，需要向第三方披露；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（五）为提供用户所要求的产品和服务，必须和第三方分享用户的注册信息；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;（六）其它符合法律规定或本协议约定的披露。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;7.4&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;在使用有点分享的过程中，请用户妥善保管个人信息，仅在必要的情形下向他人提供；如果用户发现个人信息泄露，尤其是用户账户密码发生泄露，请用户立即联络有点分享客服，以采取相应保护措施。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第八条不可抗力&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;8.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享对超出合理控制之外的原因、事件或其他因素导致的未能履行本协议下的任何义务的结果不承担责任。该等原因、事件或其他因素包括但不限于战争、台风、水灾、火灾、雷击或地震、罢工、暴动、黑客攻击、网络病毒、电信部门技术管制、政府行为、社交资源运营方的重大政策调整或任何其它自然或人为造成的灾难等情况。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;第九条法律适用与争议解决&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;9.1 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;本协议之订立、生效、解释、履行、修订与争议解决均适用中华人民共和国法律。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;9.2 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;如果发生因本协议或其违约、终止或效力而引起的或与之有关的任何争议、纠纷或索赔（以下简称&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;“&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;争议&lt;/span&gt;&lt;span style=&quot;font-size: 14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;”&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;），均应向北京市平谷区人民法院提起诉讼。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;9.3 &lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享有权受理并调解处理用户与其他用户因发布、领取任务或其他使用有点分享的行为而产生的纠纷，同时有权单方面独立判断用户间的举报及索偿是否成立，若判断索偿成立，则有点分享有权划拨用户的账户余额进行相应偿付。针对前述纠纷、举报与索赔，有点分享没有使用自用资金进行偿付的义务，但若进行了该等支付，责任用户应及时赔偿有点分享的全部损失，否则有点分享有权通过前述方式抵扣相应资金或权益，如仍无法弥补损失，则有点分享保留继续追偿的权利。因有点分享非司法机关，故有点分享对证据的鉴别能力及对纠纷的处理能力有限，受理纠纷完全是基于用户之委托，不保证处理结果符合用户的期望，&lt;/span&gt; &lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;有点分享有权自行决定是否参与争议的调处。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top:20px;margin-bottom: auto;text-align:left;line-height:24px;background:white&quot;&gt;&lt;span style=&quot;font-size:14px;font-family:宋体;color:black&quot;&gt;北京微爱传承文化发展有限公司&lt;/span&gt;&lt;span style=&quot;font-size:14px;font-family:&amp;#39;lucida Grande&amp;#39;,sans-serif;color:black&quot;&gt;&amp;nbsp; &lt;a href=&quot;http://www.youdianshare.com&quot; _src=&quot;http://www.youdianshare.com&quot;&gt;www.youdianshare.com&lt;/a&gt; &lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top: 20px; margin-bottom: auto; text-align: center; line-height: 24px; background: white;&quot;&gt;&lt;span style=&quot;font-family: Verdana; font-size: 12px; background-color: rgb(255, 255, 255);&quot;&gt;本次活动与相关奖励均与苹果公司无关&lt;/span&gt;&lt;/p&gt;";

        System.out.println("htmlEncode(str) = " + htmlEncode(str));
    }

}
