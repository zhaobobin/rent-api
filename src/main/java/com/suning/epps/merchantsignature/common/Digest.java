/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: Digest.java
 * Author:   Andy_Wang01
 * Date:     Aug 1, 2014 9:50:28 AM
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epps.merchantsignature.common;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 〈一句话功能简述〉<br>
 * 数据摘要，对传来的数据进行Md5数据摘要
 * 
 * @author Andy_Wang01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Digest {

    /**
     * 等于符号标志
     */
    private static final String EQ_SYMBOL = "=";

    /**
     * 并且符号标志
     */
    private static final String AND_SYMBOL = "&";

    /**
     * 直接对传来的字符串进行md5摘要 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param str
     * @return
     * @throws Exception
     * @throws UnsupportedEncodingException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String digest(String str) throws UnsupportedEncodingException {
        String md5 = null;
        md5 = MD5.digest(str, "UTF-8");
        return md5;
    }

    /**
     * 1.根据key对传来的map数据排序 2.生成a1=b1&a2=b2&a3=b3形式的字符串，排除某些字符串Key值 3.调用digest方法进行md5编码 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param map 要排序的字符串
     * @param key 要排序的key值
     * @return
     * @throws UnsupportedEncodingException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String digest(Map<String, String> map, List<String> excudeKeylist)
            throws UnsupportedEncodingException {
        TreeMap<String, String> treeMap = treeMap(map, excudeKeylist);
        return digest(mapToString(treeMap));
    }

    /**
     * 
     * 功能描述: <br>
     * 将map按key字符串排序的treeMap
     * 
     * @param map
     * @param keys
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static TreeMap<String, String> treeMap(Map<String, String> map, List<String> excudeKeylist) {
        // 初始化字符串比较器
        Comparator<String> stringComparator = new StringComparator();
        TreeMap<String, String> treeMap = new TreeMap<String, String>(stringComparator);
        treeMap.putAll(map);
        // 移除非摘要的key
        if (null != excudeKeylist && excudeKeylist.size() > 0) {
            for (String key : excudeKeylist) {
                treeMap.remove(key);
            }
        }
        return treeMap;
    }

    /**
     * 
     * 功能描述: <br>
     * 将map转成a1=b1&a2=b2&a3=b3形式的字符串
     * 
     * @param map
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String mapToString(Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue() == null ? "" : entry.getValue().trim();
            result.append(entry.getKey()).append(EQ_SYMBOL).append(value).append(AND_SYMBOL);
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString().trim();
    }
}
