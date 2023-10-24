/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: SignatureUtil.java
 * Author:   12061751
 * Date:     2015-11-05 下午02:24:39
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epps.merchantsignature;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

import com.suning.epps.merchantsignature.common.Digest;
import com.suning.epps.merchantsignature.common.RSAUtil;

/**
 * RSA加签验签模板工具<br>
 * 
 * @author 12061751
 * @see [相关类/方法]（可选）
 * @since [2015-11-05] （可选）
 */
public class SignatureUtil {

    /**
     * 和EPP业务对接,业务字段排序并且MD5摘要,做为待签名的字符串 <br>
     * 建议客户端打印返回的摘要值,便于和EPP进行比对<br>
     * 1、把map排序然后拼接成一个字符串.以"="拼接键值对的key和value,以"&"拼接各个键值对<br>
     * 2、对该字符串进行MD5摘要,已转大写<br>
     * 
     * @param map 待加签的map
     * @return MD5摘要值
     * @throws Exception
     */
    public static String digest(Map<String, String> map) throws Exception {
        // 参数检查
        if (map == null || map.size() == 0) {
            throw new Exception("业务字段为空");
        }
        // 返回签名字符串
        try {
            return Digest.digest(map, null);
        } catch (Exception e) {
            throw new Exception("业务字段MD5摘要异常", e);
        }
    }

    /**
     * 和EPP业务对接,业务字段排序并且MD5摘要,做为待签名的字符串 <br>
     * 建议客户端打印返回的摘要值,便于和EPP进行比对<br>
     * 1、把map剔除excudeKeylist的key值<br>
     * 2、把map排序然后拼接成一个字符串.以"="拼接key和value,以"&"拼接各个键值对<br>
     * 3、对该字符串进行MD5摘要,已转大写<br>
     * 
     * @param map 待加签的map
     * @param excudeKeylist 需要排除签名的key值list
     * @return MD5摘要值
     * @throws Exception
     */
    public static String digest(Map<String, String> map, List<String> excudeKeylist) throws Exception {
        // 参数检查
        if (map == null || map.size() == 0) {
            throw new Exception("业务字段为空");
        }
        // 返回签名字符串
        try {
            return Digest.digest(map, excudeKeylist);
        } catch (Exception e) {
            throw new Exception("业务字段MD5摘要异常", e);
        }
    }

    /**
     * 基本的RSA加签方法<br>
     * 对data数据进行加签
     * 
     * @param data 待加签的字符串
     * @param privateKeyStr 私钥字符串
     * @return 签名值
     * @throws Exception
     */
    public static String sign(String data, String privateKeyStr) throws Exception {
        // 参数检查
        if (data == null || data.length() == 0) {
            throw new Exception("待加签参数为空");
        }
        if (privateKeyStr == null || privateKeyStr.length() == 0) {
            throw new Exception("私钥字符串为空");
        }
        try {
            // 获取根据私钥字符串获取私钥
            PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyStr);
            // 返回签名字符串
            return RSAUtil.sign(data, privateKey);
        } catch (Exception e) {
            throw new Exception("加签异常", e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 对数据进行验签 1.对Base64公钥进行解码 2.数据验签
     * 
     * @param signData
     * @param signature
     * @param publicKey
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean verifySignature(String signData, String signature, String publicKey) throws Exception {
        // 参数检查
        if (signData == null || signData.length() == 0) {
            throw new Exception("原数据字符串为空");
        }
        if (signature == null || signature.length() == 0) {
            throw new Exception("签名字符串为空");
        }
        if (publicKey == null || publicKey.length() == 0) {
            throw new Exception("公钥字符串为空");
        }
        try {
            // 获取根据公钥字符串获取私钥
            PublicKey pubKey = RSAUtil.getPublicKey(publicKey);
            // 返回验证结果
            return RSAUtil.vertiy(signData, signature, pubKey);
        } catch (Exception e) {
            throw new Exception("验证签名异常", e);
        }
    }

}
