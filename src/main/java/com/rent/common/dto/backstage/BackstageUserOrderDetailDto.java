package com.rent.common.dto.backstage;

import com.rent.common.dto.order.*;
import com.rent.common.dto.user.UserEmergencyContactDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 9:41:08
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "后台订单详情")
public class BackstageUserOrderDetailDto implements Serializable {

    private static final long serialVersionUID = -103587609350050838L;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "订单信息")
    private OpeUserOrderInfoDto userOrderInfoDto;

    @Schema(description = "商家信息")
    private OpeShopInfoDto shopInfoDto;

    @Schema(description = "订单收货人信息")
    private OrderAddressDto orderAddressDto;

    @Schema(description = "发货物流信息")
    private OpeExpressInfo receiptExpressInfo;

    @Schema(description = "归还物流信息")
    private OpeExpressInfo giveBackExpressInfo;

    @Schema(description = "商品信息")
    private OpeOrderProductInfo productInfo;

    @Schema(description = "订单金额信息")
    private UserOrderCashesDto userOrderCashesDto;

    @Schema(description = "账单信息")
    private List<OrderByStagesDto> orderByStagesDtoList;

    @Schema(description = "买断信息")
    private UserOrderBuyOutDto orderBuyOutDto;

    @Schema(description = "结算信息")
    private OrderSettlementInfoDto settlementInfoDto;

    @Schema(description = "增值服务信息")
    private OrderAdditionalServicesDto orderAdditionalServicesDto;

    @Schema(description = "增值服务信息集合")
    private List<OrderAdditionalServicesDto> orderAdditionalServicesList;

    @Schema(description = "紧急联系人列表")
    private List<UserEmergencyContactDto> userEmergencyContactList;

    @Schema(description = "租用时间")
    private Integer rentDuration;

    @Schema(description = "起租时间")
    private Date rentStart;

    @Schema(description = "归还时间")
    private Date unrentTime;

    @Schema(description = "合同地址")
    private String contractUrl;

    private String nsfLevel;



    private AntChainDto antChainInfo;

    @Schema(description = "下单用户当前位置实体")
    private OrderLocationAddressDto orderLocationAddress;

    private Boolean showQueryRiskButton;
}
