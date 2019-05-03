package com.jokerdata.common.utils;

import org.apache.coyote.RequestInfo;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.servlet.http.HttpServletRequest;

/**
 * Created by xushuyi on 2017/3/13.
 */
public class CommonUtil {
    // 手机号码判断
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 邮箱格式判断
    public static boolean isEmail(String email) {
        String str = "^[0-9a-zA-Z_][-_\\.0-9a-zA-Z-]{0,63}@([0-9a-zA-Z][0-9a-zA-Z-]*\\.)+[a-zA-Z]{2,4}$";
        System.out.println(str);
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //用户账号格式判断
    public static boolean isUserAccount(String userAccount) {
        String str = "^[a-zA-Z_]{1}\\w{5,15}$";//tiansankun 描述：注册用户名格式校验修改
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(userAccount);
        return m.matches();
    }

    //用户密码判断
    public static boolean isUserPwd(String userpwd) {
        String str = "^[\\w-]{6,16}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(userpwd);
        return m.matches();
    }

    //真实姓名格式判断
    public static boolean isUserName(String userName) {
        userName = userName.replaceAll(" ", "");
        String str = "^[\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\\·]{0,48}[\u4e00-\u9fa5]{1}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    //护照格式判断
    public static boolean isPassport(String passport) {
        passport = passport.replaceAll(" ", "");
        String str = "^([a-zA-Z]{5,17})|([a-zA-Z0-9]{5,17})$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(passport);
        return m.matches();
    }

    //邮编格式判断
    public static boolean isZipCode(String zipCode) {
        String str = "^\\d{6}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(zipCode);
        return m.matches();
    }

    //组织机构代码
    public static boolean isOrganizeCode(String organizeCode) {
        String str = "^([0-9A-Z]){8}[-]?[0-9|X]$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(organizeCode);
        return m.matches();
    }

    //座机电话
    public static boolean isOfficePhone(String officePhone) {
        String str = "(^(\\d{11})$|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(officePhone);
        return m.matches();
    }

    //金额
    public static boolean isRegisteredCapital(String registeredCapital) {
        String str = "^(([1-9]\\d{0,11})|0)(\\.\\d{1,2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(registeredCapital);
        return m.matches();
    }

    // 性别
    public static boolean isSex(String sex) {
        if (sex.equals("1") || sex.equals("2")) {
            return true;
        }
        return false;
    }

    // 验证字符串是否是BigDecimal
    public static boolean isBigDecimal(String str) {
        Matcher match = null;
        if (isNumeric(str) == true) {
            Pattern pattern = Pattern.compile("[0-9]*");
            match = pattern.matcher(str.trim());
        } else {
            if (str.trim().indexOf(".") == -1) {
                Pattern pattern = Pattern.compile("^[+]?[0-9]*");
                match = pattern.matcher(str.trim());
            } else {
                Pattern pattern = Pattern.compile("^[+]?[0-9]+(\\.\\d{1,100}){1}");
                match = pattern.matcher(str.trim());
            }
        }
        return match.matches();
    }

    // 银行账号校验
    public static boolean isBankAccNo(String str) {
        if (!isNumeric(str)) {
            return false;
        }
        if (str.length() != 15 && str.length() != 16 && str.length() != 19 && str.length() != 23) {
            return false;
        }

        return true;
    }

    /*
     * 功能：身份证的有效验证
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public static String IDCardValidate(String IDStr, Date birthday, String sex) throws ParseException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        //性别位
        int gender = 0;
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
            gender = Integer.parseInt(IDStr.substring(16, 17)) % 2 == 1 ? 1 : 2;
        } else if (IDStr.length() == 15) {
            gender = Integer.parseInt(IDStr.substring(14, 15)) % 2 == 1 ? 1 : 2;
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        //性别判断
        if (!StringUtils.isEmpty(sex) && !String.valueOf(gender).equals(sex)) {
            errorInfo = "身份证号码与性别不匹配!";
            return errorInfo;
        }
        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }

        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

        if (birthday != null && !(strYear + "-" + strMonth + "-" + strDay).equals(s.format(birthday))) {
            errorInfo = "身份证号码与出生日期不匹配!";
            return errorInfo;
        }

        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                || (gc.getTime().getTime() - s.parse(
                strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
            errorInfo = "身份证生日不在有效范围。";
            return errorInfo;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr.toLowerCase()) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    public
    static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回浏览器的IP地址<br/>
     * 请求有可能经过Apache代理模式，request.getRemoteAddr()返回的不一定是浏览器的IP地址
     *
     * @param request HttpServletRequest实例
     * @return 浏览器的IP地址
     */
//    public final static String getClientIPAddress(HttpServletRequest request) {
//        String address = request.getHeader("X-Forwarded-For");
//        if (address != null) {
//            int iFirstDot = address.indexOf(',');
//            if (iFirstDot > 0) {
//                address = address.substring(0, iFirstDot);
//            }
//            if (isIPv4Address(address)) {
//                return address;
//            }
//        }
//        return request.getRemoteAddr();
//    }

    /**
     * 是否是ipv4地址
     *
     * @param str 字符串
     * @return
     */
    public final static boolean isIPv4Address(String str) {
        if (str != null) {
            int dot1 = str.indexOf('.');
            if (dot1 <= 0) {
                return false;
            }
            int temp;
            try {
                temp = Integer.parseInt(str.substring(0, dot1++));
                if (temp < 0 || temp > 255) {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }

            int dot2 = str.indexOf('.', dot1);
            if (dot2 <= 0) {
                return false;
            }
            try {
                temp = Integer.parseInt(str.substring(dot1, dot2++));
                if (temp < 0 || temp > 255) {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }

            int dot3 = str.indexOf('.', dot2);
            if (dot3 <= 0) {
                return false;
            }
            try {
                temp = Integer.parseInt(str.substring(dot2, dot3++));
                if (temp < 0 || temp > 255) {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
            try {
                temp = Integer.parseInt(str.substring(dot3));
                if (temp < 0 || temp > 255) {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 两个时间相差的秒数
     *
     * @param begin
     * @param end
     * @return
     */
    public static long compareDifferSecond(Date begin, Date end) {
        if (begin == null || end == null) {
            return 0;
        }
        long second = (end.getTime() - begin.getTime()) / 1000;    //转换成秒
        return second;
    }

    /**
     * 两个时间相差的小时数
     *
     * @param begin
     * @param end
     * @return
     */
    public static long compareDifferHours(Date begin, Date end) {
        if (null == begin || null == end) {
            return -1;
        }
        long intervalMilli = end.getTime() - begin.getTime();
        return intervalMilli / (60 * 60 * 1000);
    }

    /**
     * 将geUserPersonal里的性别1,2转换为字母代号，M、F
     */
    public static String convertSexToWord(String sex) {
        if ("1".equals(sex)) {
            return "M";
        } else if ("2".equals(sex)) {
            return "F";
        } else {
            return "";
        }
    }

    /**
     * 判断一个对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (("").equals(obj) || null == obj || "null".equals(obj) || obj.equals(null)) {
            return true;
        }
        return false;
    }



    /**
     * 错误信息
     */
    private static String errorMsg;

    public static String getVerifyPageError() {
        return errorMsg;
    }

    public static void setVerifyPageError(String msg) {
        errorMsg = msg;
    }

    /**
     * 输出详细日志
     *
     * @param anexcepObj
     * @return
     */
    public static String getExceptionStackTrace(Throwable anexcepObj) {
        StringWriter sw = null;
        PrintWriter printWriter = null;
        try {
            if (anexcepObj != null) {
                sw = new StringWriter();
                printWriter = new PrintWriter(sw);
                anexcepObj.printStackTrace(printWriter);
                printWriter.flush();
                sw.flush();
                return sw.toString();
            } else
                return null;
        } finally {

            try {
                if (sw != null)
                    sw.close();
                if (printWriter != null)
                    printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};

    /**
     * 根据出生日期获取星座
     *
     * @return
     */
    public static String getConstellation(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }
    public static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }

}