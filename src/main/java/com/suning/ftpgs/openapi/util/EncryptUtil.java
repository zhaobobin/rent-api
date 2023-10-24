package com.suning.ftpgs.openapi.util;

import com.suning.epps.merchantsignature.common.HexUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

/**
 * EncryptUtil
 */
public class EncryptUtil {
    private static final String ALGORITHM_AES = "AES";
    private static final String UTF8_CHARSET = "UTF-8";
    private static final String KEY_ALGORITHM = "RSA";

    private EncryptUtil() {
    }

    /**
     * base64编码AES加密后的字符串
     *
     * @param content 需要加密的字符串
     * @param strKey  秘钥key
     */
    public static String encryptBase64DecorateAES(String content, String strKey) {
        byte[] encryptAesBytes = encrypt (content, strKey);
        return byte2hex (encryptAesBytes);
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param strKey  加密秘钥
     * @return 加密后的比特流
     */
    private static byte[] encrypt(String content, String strKey) {
        try {
            SecretKeySpec key = new SecretKeySpec (strKey.getBytes (), ALGORITHM_AES); //NOSONAR
            Cipher cipher = Cipher.getInstance (ALGORITHM_AES);
            byte[] byteContent = content.getBytes (UTF8_CHARSET);
            cipher.init (Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal (byteContent);
        } catch (Exception e) {
            throw new FtpgsUtils.FtpgsException ("use AES algorithm encrypt error", e);
        }

    }

    /**
     * 解密经过base64编码后的AES加密过的字符串
     *
     * @param content    待解密的经过base64编码后的AES加密过的字符串
     * @param secretSeed 秘钥种子
     * @return 原始内容
     */
    public static String decryptBase64DecorateAES(String content, String secretSeed) {
        byte[] decryptResult = decrypt (hex2byte (content), secretSeed);
        return new String (decryptResult); //NOSONAR
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param strKey  解密密钥
     * @return 解密后的
     */
    private static byte[] decrypt(byte[] content, String strKey) {
        try {
            SecretKeySpec key = new SecretKeySpec (strKey.getBytes (), ALGORITHM_AES);//NOSONAR
            Cipher cipher = Cipher.getInstance (ALGORITHM_AES);
            cipher.init (Cipher.DECRYPT_MODE, key);
            return cipher.doFinal (content);
        } catch (Exception e) {
            throw new FtpgsUtils.FtpgsException ("use AES algorithm decrypt error", e);
        }
    }

    private static byte[] hex2byte(final String str) {
        if (str == null) {
            return new byte[]{};
        }
        String newStr = str.trim ();
        int len = newStr.length ();
        if (len <= 0 || len % 2 != 0) {
            return new byte[]{};
        }
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < newStr.length (); i += 2) {
                b[i / 2] = (byte) Integer.decode ("0x" + newStr.substring (i, i + 2)).intValue ();
            }
            return b;
        } catch (Exception e) { //NOSONAR
            return new byte[]{};
        }
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder ();
        for (byte bi : b) {
            String temp = Integer.toHexString (bi & 0XFF);
            if (temp.length () == 1) {
                hs.append ("0");
            }
            hs.append (temp);
        }
        return hs.toString ().toUpperCase ();
    }


    /**
     * 加密<br>
     * 使用易付宝公钥加密
     *
     * @param data 加密的数据
     * @param key  公钥
     * @return 返回加密后的字符串
     */
    public static String encryptByPublicKey(String data, String key) {
        try {
            // 对公钥解密
            byte[] keyBytes = decryptBASE64 (key);
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec (keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance (KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic (x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance (keyFactory.getAlgorithm ());
            cipher.init (Cipher.ENCRYPT_MODE, publicKey);
            return HexUtil.bytes2Hexstr (cipher.doFinal (data.getBytes ()));
        } catch (Exception e) {
            throw new FtpgsUtils.FtpgsException ("rsa加密失败", e);
        }
    }

    private static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64 (key);
    }

    /**
     * 生成16位不重复的随机数，含数字+大小写
     * 作为AES密钥使用
     */
    public static String getAesKey() {
        StringBuilder uid = new StringBuilder ();
        //产生16位的强随机数
        Random rd = new SecureRandom ();
        for (int i = 0; i < 16; i++) {
            int type = rd.nextInt (3);
            switch (type) {
                case 0:
                    uid.append (rd.nextInt (10));
                    break;
                case 1:
                    uid.append ((char) (rd.nextInt (25) + 65));
                    break;
                case 2:
                    uid.append ((char) (rd.nextInt (25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString ();
    }

}
