package com.rent.common.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 10:59:15
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "物流信息")
public class OpeExpressInfo implements Serializable {

    private static final long serialVersionUID = 1824230734647299495L;

    @Schema(description = "物流公司")
    private String expressCompany;

    @Schema(description = "物流公司id")
    private Long expressId;

    @Schema(description = "物流编号")
    private String expressNo;

    @Schema(description = "物流公司简称")
    private String shortName;

    @Schema(description = "发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deliveryTime;

    @Schema(description = "收货人手机号后四位")
    private String receiverPhone;
}
