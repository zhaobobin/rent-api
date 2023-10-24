package com.rent.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @description: 脱敏工具类
 * @author: yr
 * @create: 2021-04-16 10:50
 **/
public class DesensitizationUtil {

    /**
     * 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName
     * @param  index 1 为第index位开始脱敏
     * @return
     */
    public static String left(String fullName,int index) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, index);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * 110****58，前面保留3位明文，后面保留2位明文
     *
     * @param name
     * @param index 3
     * @param end 2
     * @return
     */
    public static String around(String name,int index,int end) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        String left = StringUtils.left(name, index);
        String right = StringUtils.right(name, end);

        StringBuilder stringBuilder = new StringBuilder(left);
        int count = name.length()-index-end;
        for (int i = 0; i < count; i++) {
            stringBuilder = stringBuilder.append("*");
        }
        stringBuilder.append(right);
        return stringBuilder.toString();
    }

    public static String nameEncrypt(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        if(name.length()<=2){
            return left(name,1);
        }else {
            return around(name,1,1);
        }
    }

    /**
     * 后四位，其他隐藏<例子：****1234>
     *
     * @param num
     * @return
     */
    public static String right(String num,int end) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, end), StringUtils.length(num), "*");
    }
    // 手机号码前三后四脱敏
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    //身份证前三后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    //护照前2后3位脱敏，护照一般为8或9位
    public static String idPassport(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*") + id.substring(id.length() - 3);
    }
    /**
     * 证件后几位脱敏
     * @param id
     * @param sensitiveSize
     * @return
     */
    public static String idPassport(String id, int sensitiveSize) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        int length = StringUtils.length(id);
        return StringUtils.rightPad(StringUtils.left(id, length - sensitiveSize), length, "*");
    }
}
