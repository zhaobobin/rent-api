package com.rent.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @author boan
 */
public class RSA {

    private static final String SIGN_ALGORITHMS = "SHA256withRSA";
    private static final String CHAR_SET = "UTF-8";

    public static String sign(String content, String privateKey) throws Exception {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(CHAR_SET));
            byte[] signed = signature.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        }
    }

    /**
     * 1.第json的key 字典排序
     * 2.按排完序的key  获取对应的 value 拼接成字符串
     *
     * @param json
     * @return
     */
    public static String getSignContent(JSONObject json) {
        List<String> keys = new ArrayList<>(json.size());
        json.forEach((s, v) -> {
            if (!"sign".equals(s)) {
                keys.add(s);
            }
        });
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        keys.forEach(key -> {
            Object v = json.get(key);
            if (v instanceof JSONObject || v instanceof JSONArray) {
                sb.append(JSON.toJSONString(v));
            } else {
                sb.append(v == null ? "" : v.toString());
            }
        });
        return sb.toString();
    }

}







