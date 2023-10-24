package com.rent.common.dto.components.dto;

import com.rent.common.enums.product.AntChainProductClassEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "蚂蚁链同步订单信息")
public class AntChainSyncOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    private String orderCreateTime;
    private String orderPayTime;
    private String orderPayId;
    private Long depositFree;
    private Long acutalPreAuthFree;
    private Integer rentTerm;
    private Long rentPricePerMonth;
    private Long buyOutPrice;
    private String leaseContractUrl;
    private String userAddress;
    private Integer provinceCode;
    private Integer cityCode;
    private Integer districtCode;
    private Long totalRentMoney;
    private String leaseCorpName;
    private String leaseCorpId;
    private String leaseCorpOwnerName;
    private String productId;
    private String serialNumber;
    private Long costPrice;
    private String productName;
    private Long productPrice;
    private String productModel;
    private Long depositPrice;
    private String uid;
    private String loginTime;
    private String userName;
    private String idCard;
    private String userIdCardFrontObjectKey;
    private String phone;
    private String alipayUID;
    private String yidunScore;
    private AntChainProductClassEnum antChainProductClassEnum;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
