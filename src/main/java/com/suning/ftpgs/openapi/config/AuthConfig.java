package com.suning.ftpgs.openapi.config;

import com.suning.ftpgs.openapi.util.DateFormat;
import com.suning.ftpgs.openapi.util.FtpgsUtils;

import java.util.Date;
import java.util.Map;

/**
 *  鉴权配置参数
 */
public class AuthConfig {

    private AuthConfig() {
    }


    /**
     * 此配置项，为通用配置
     */
    static public FtpgsUtils.AuthParam getAuthConfig(Map<String, Object> bizMap, String url) {
        FtpgsUtils.AuthParam authParam = new FtpgsUtils.AuthParam ();
        authParam
/*           .setAppId ("【商户应用标识，苏宁金融分配给商户的appId】")
                .setPrivateKey ("【商户自行生成公私钥对后,保留的私钥】")
                .setPublicKey ("【苏宁金融网关平台demo中提供的易付宝公钥】")
                .setVersion ("【默认:1.0】")
                .setSignKeyIndex ("【绑定商户公钥的索引号,苏宁金融提供】")
                .setSignType ("【签名方式，默认:RSA2】")
                .setTimestamp ("【时间戳，格式：yyyyMMddHHmmss，例如：20190805093412，默认当前时间】")
                .setUrl ("【苏宁金融提供的HTTP接口地址】")*/

                .setAppId ("yfbm70056575e2018060401")
                .setPrivateKey ("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMMaRKMwh+vOHjmkOFsntsJNTH+yBOgbIeFgHIt+KCV6vqu64N2KyveT4MYhR89m7rv9qIt0YSmysXBWzZMng8MS8y8Uo5rkM7BrlTRpF1FaxAvc9hXQunwqHrB3axS+/mqRAKnGJ2hGVf3HyoNrpJ3N1kmHurf3PhLTzkKB0L25AgMBAAECgYBeDgnMyhacfQ9hJmPsveaDIRSh8sYexwW8SsM4FCdyegv1afABnWlflTClE2I1ACBTDGP2ZrJ4kaasy4vrRv9jcee77lNvKb6GVlzRgZUmm8fPyqKsCQ/edVfL4vKM7UMs/YbYiun/5gvjZuRM3v3OBYRRirheujcYKrvmPIZcAQJBAOaNfI/0g6gOVuqtqQS1S4ksM88X9x6MAbUf2quSxaqRCjkkFnj3AQe438hyoKIeLQnOpXOLNmF0ZTP6SEZBrUECQQDYoxoKC4gdGTYBjSq48D3ZpNgl1P2fXhxK6EykZZ9x5n65GLrkzepMiTzD/Az1vqpuSIDfMaZZ1Bhni6C1nVp5AkEAqqV22SZmfHGAq9s2CyIVcsag4lwesSF38hRNykisvf70zi6D460PuiOAn+EYPGaRd3zYwZj8+00nyz0pWbcrAQJBANGouBC0rFyNA6lot8oYJ6O1V8L1aSeNaxBL1bDc8PzIuCfm+Slq57B+uSJrkxdaGZN189MOOBKDo8LCbRyXVvECQQCvwD/zrx3PddwSWaHdg6ONVomgeYEkAG/ZH7DVPt5i+skZBj5N9i0AsWBCrebmQ83D2s1iAhnzRtvUnBrXuVX9")
                .setPublicKey ("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmlcnbcT+nOWsRwCNMdJdCfGhW1WmYRfBJpUJGQRtuQsJRtRqe1XvTzkn7H2z9x4pRyKA7k9R63ZilYBMIUVgaR4zDGOQqb5O8RrWa/8o/obQH8cZ/be0vd0IXni7gDeVjtaU441tuXQdGpUC4BLuLGM4U8TvNLzPiZxlVi3eJ+wIDAQAB")
                .setVersion ("1.0")
                .setSignKeyIndex ("0014")
                .setSignType ("RSA2")
                .setTimestamp (DateFormat.formatFromDate(new Date()))
                .setUrl(url)
                .setParams (bizMap);//设置业务参数Map
        return authParam;
    }



}
