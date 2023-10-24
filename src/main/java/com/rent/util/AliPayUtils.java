package com.rent.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.rent.config.outside.OutsideConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author <a href="mailto:boannpx@163.com">niupq</a>
 * Date: 2019/5/13
 * Desc:支付宝支付工具类
 * Version:V1.0
 */
@Data
@Slf4j
public class AliPayUtils {

    /**
     * @param contents 密文
     * @return 原文
     */
    public static String decrypt(String contents) throws Exception {
//        return  "{\"code\":\"10000\",\"msg\":\"Success\",\"mobile\":\"12345678911\"}";
        Map<String, String> openapiResult = JSON.parseObject(contents, new TypeReference<Map<String, String>>() {
        }, Feature.OrderedField);
        String charset = StringUtils.defaultIfBlank(openapiResult.get("charset"), "UTF-8");
        String encryptType = StringUtils.defaultIfBlank(openapiResult.get("encryptType"), "AES");
        String content = openapiResult.get("response");
        //如果密文的
        boolean isDataEncrypted = !content.startsWith("{");
        //3. 解密
        String plainData = null;
        if (isDataEncrypted) {
            try {
                plainData = AlipayEncrypt.decryptContent(content, encryptType, OutsideConfig.ALIPAY_INTERFACE_CONTENT_KEY, charset);
            } catch (AlipayApiException e) {
                //解密异常, 记录日志
                throw new Exception("解密异常");
            }
        } else {
            plainData = content;
        }
        return plainData;
    }

    /**
     * 修正乱码问题
     *
     * @return
     */
    public static Map<String, String> amendCodeMap(Map<String, String[]> requestParams) {

        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet()
            .iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //如果出现乱码则生成这个
            //            try {
            //                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            //            } catch (UnsupportedEncodingException e) {
            //                e.printStackTrace();
            //            }
            params.put(name, valueStr);
        }
        return params;
    }
}
