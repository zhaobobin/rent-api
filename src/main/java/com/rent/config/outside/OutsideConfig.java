package com.rent.config.outside;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OutsideConfig {

    public static String APP_NAME;//小程序名称
    public static String APP_LOGO_URL;//小程序LOGO URL
    public static String ALIPAY_SEARCH_PNG;//小程序 支付宝 搜索 图片

    public static String APP_CODE;//小程序名称拼音首字母（大写）
    public static String DOMAIN;//域名
    public static String COMPANY;//公司名称
    public static String ADDRESS;//公司地址
    public static String LICENSE_NO;//公司营业执照编号
    public static String SIGN_CITY;//公司地址所在地级市
    public static String BANK;//公司公户开户行
    public static String BANK_CARD_NO;//公司公户卡号
    public static String LEGAL_NAME;//法人姓名
    public static String LEGAL_ID_NO;//法人身份证号码
    public static String LEGAL_PHONE;//法人电话
    public static String CONTRACT_PHONE;//客服电话
    public static String E_MAIL;//邮箱
    public static String ALIPAY_ACCOUNT;//支付宝账号


    //请求url
    //支付宝唯一用户号
    public static String SERVICEID;//芝麻免押服务id
    public static String PARENT_ID; //小程序PID
    public static String APPID; //小程序APPID
    public static String ALIPAY_INTERFACE_CONTENT_KEY; //接口内容加密方式
    public static String ALIPAY_CERT_ROOT;//小程序证书
    public static String ALIPAY_CERT_PUBLIC;//小程序证书
    public static String APP_CERT_PUBLIC;//小程序证书
    //小程序私钥
    public static String PRIVATE_KEY;
    //支付宝相关配置

    public static String TEMP_FILE_DIR = System.getProperty("user.dir") + File.separator + "temp";
    @Value("${outside.app_name}")
    public void setAppName(String app_name){
        OutsideConfig.APP_NAME = app_name;
    }

    @Value("${outside.app_logo_url}")
    public void setAppLogoUrl(String app_logo_url){
        OutsideConfig.APP_LOGO_URL = app_logo_url;
    }

    @Value("${outside.alipay_search_png}")
    public void setAlipaySearchPng(String alipay_search_png){
        OutsideConfig.ALIPAY_SEARCH_PNG = alipay_search_png;
    }


    @Value("${outside.app_code}")
    public void setAppCode(String app_code){
        OutsideConfig.APP_CODE = app_code;
    }
    @Value("${outside.domain}")
    public void setDomain(String domain){
        OutsideConfig.DOMAIN = domain;
    }
    @Value("${outside.company}")
    public void setCompany(String company){
        OutsideConfig.COMPANY = company;
    }
    @Value("${outside.address}")
    public void setAddress(String address){
        OutsideConfig.ADDRESS = address;
    }
    @Value("${outside.license_no}")
    public void setLicenseNo(String license_no){
        OutsideConfig.LICENSE_NO = license_no;
    }
    @Value("${outside.sign_city}")
    public void setSignCity(String sign_city){
        OutsideConfig.SIGN_CITY = sign_city;
    }
    @Value("${outside.bank}")
    public void setBank(String bank){
        OutsideConfig.BANK = bank;
    }
    @Value("${outside.bank_card_no}")
    public void setBankCardNo(String bank_card_no){
        OutsideConfig.BANK_CARD_NO = bank_card_no;
    }
    @Value("${outside.legal_name}")
    public void setLegalName(String legal_name){
        OutsideConfig.LEGAL_NAME = legal_name;
    }
    @Value("${outside.legal_id_no}")
    public void setLegalIdNo(String legal_id_no){
        OutsideConfig.LEGAL_ID_NO = legal_id_no;
    }
    @Value("${outside.legal_phone}")
    public void setLegalPhone(String legal_phone){
        OutsideConfig.LEGAL_PHONE = legal_phone;
    }
    @Value("${outside.contract_phone}")
    public void setContractPhone(String contract_phone){
        OutsideConfig.CONTRACT_PHONE = contract_phone;
    }
    @Value("${outside.e_mail}")
    public void setEMail(String e_mail){
        OutsideConfig.E_MAIL = e_mail;
    }
    @Value("${outside.alipay_account}")
    public void setAlipayAccount(String alipay_account){
        OutsideConfig.ALIPAY_ACCOUNT = alipay_account;
    }
    @Value("${outside.serviceid}")
    public void setServiceid(String serviceid){
        OutsideConfig.SERVICEID = serviceid;
    }
    @Value("${outside.parent_id}")
    public void setParentId(String parent_id){
        OutsideConfig.PARENT_ID = parent_id;
    }
    @Value("${outside.appid}")
    public void setAppid(String appid){
        OutsideConfig.APPID = appid;
    }
    @Value("${outside.alipay_interface_content_key}")
    public void setAlipayInterfaceContentKey(String alipay_interface_content_key){
        OutsideConfig.ALIPAY_INTERFACE_CONTENT_KEY = alipay_interface_content_key;
    }
    @Value("${outside.alipay_cert_root}")
    public void setAlipayCertRoot(String alipay_cert_root){
        OutsideConfig.ALIPAY_CERT_ROOT = alipay_cert_root;
    }
    @Value("${outside.alipay_cert_public}")
    public void setAlipayCertPublic(String alipay_cert_public){
        OutsideConfig.ALIPAY_CERT_PUBLIC = alipay_cert_public;
    }
    @Value("${outside.app_cert_public}")
    public void setAppCertPublic(String app_cert_public){
        OutsideConfig.APP_CERT_PUBLIC = app_cert_public;
    }
    @Value("${outside.private_key}")
    public void setPrivateKey(String private_key){
        OutsideConfig.PRIVATE_KEY = private_key;
    }
}