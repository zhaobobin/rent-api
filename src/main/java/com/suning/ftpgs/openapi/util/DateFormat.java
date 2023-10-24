package com.suning.ftpgs.openapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程安全的日期格式化
 */
public final class DateFormat {

    /**
     * 时间格式化对象
     */
    public static final DateFormat DATE_TIME_FORMAT = new DateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateFormat DATE_FORMAT = new DateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DATE_FORMAT2 = new DateFormat("yyyy-MM-dd");

    public static final  DateFormat TranDateSim = new DateFormat("yyyyMMdd");
    public static final  DateFormat TranTimeSim = new DateFormat("HHmmssSSS");
    public static final  DateFormat TranTimeSs = new DateFormat("HHmmss");

    //日期时间格式化对象(14位)
    public static final DateFormat FORMAT_DATE_TIME = new DateFormat("yyyyMMddHHmmss");


    /**
     * 日期格式
     */
    private String pattern;

    /**
     * 存放当前线程日期格式化对象的容器
     */
    private ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(pattern);
        }
    };

    /**
     * 初始化日期格式
     *
     * @param pattern 日期格式
     */
    public DateFormat(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 解析日期
     *
     * @param source 含日期内容的字符串
     * @return 日期对象
     * @throws ParseException 解析异常
     */
    public Date parse(String source) throws ParseException {
        return this.local.get().parse(source);
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public String format(Date date) {
        return this.local.get().format(date);
    }

    /**
     * 格式化日期
     *
     * @param datetime 日期对象(字符串例如"2017-11-09 09:56:10")
     * @return 日期字符串
     */
    public String formatFromStr(String datetime) {
        //String格式化为Date
        Date date = null;
        try {
            date = DateFormat.DATE_FORMAT.parse(datetime);
        } catch (ParseException e) {
        }
        return this.local.get().format(date);
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象(字符串例如"2017-11-09 09:56:10")
     * @return 日期字符串
     */
    public static String formatFromDate(Date date) {
        //String格式化为Date
        return date == null?null:DateFormat.FORMAT_DATE_TIME.format(date);
    }
}
