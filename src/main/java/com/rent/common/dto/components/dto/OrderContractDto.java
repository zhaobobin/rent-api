package com.rent.common.dto.components.dto;

import com.rent.common.dto.order.OrderByStagesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "订购合同")
public class OrderContractDto {
    private String contractNo;
    private String shopName;
    private String userName;
    private String shopAddress;
    private String shopServiceTel;
    private String idNo;
    private String telephone;
    private String orderId;
    private String reciveName;
    private String reciveAddress;
    private String reciveCity;
    private String productName;
    private String skuTitle;
    private String originalRent;
    private String totalRent;
    private String totalPeriod;
    private String deposit;
    private String salePrice;
    private String createTime;
    private String platformName;
    /**
     * 平台营业执照编号
     */
    private String platformLicenseNo;
    /**
     * 平台法人
     */
    private String platformLegalIdNo;
    private String platformLegalPhone;
    private String platformContactTelephone;
    /**
     * 平台法人身份证号
     */
    private String platformLegalName;
    private String platformAddress;
    private String platform;
    private String company;
    private String key;
    private String buyOutPrice;
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 营业执照编号
     */
    private String businessLicenseNo;
    /**
     * 店铺法人
     */
    private String shopLegalIdNo;
    private String shopLegalPhone;
    /**
     * 店铺法人身份证号
     */
    private String shopLegalName;

    private String signAddress;

    @Schema(description = "分期账单")
    private List<OrderByStagesDto> orderByStagesDtoList;


}
