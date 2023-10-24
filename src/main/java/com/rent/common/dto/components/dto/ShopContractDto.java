package com.rent.common.dto.components.dto;

import lombok.Data;

/**
 * @author zhaowenchao
 */
@Data
public class ShopContractDto {
    private String name;
    private String businessLicenseNo;
    private String contactName;
    private String contactTelephone;
    private String licenseAddress;
    private String zfbName;
    private String zfbCode;
    private String createTime;
    private String contractNo;
    private String start;
    private String end;

    private String shopId;
    private String idNo;
    private String userName;

    private String platformRate;
    private String shopRate;


    private String platformName;
    private String platformEnterpriseName;
    private String platformLicenseNo;
    private String platformContactName;
    private String platformContactTelephone;
    private String platformContactIdNo;
    private String platformAddress;

    private String platformBank;
    private String platformBankCardNo;
    private String platformAlipay;

    private String signAddress;

}
