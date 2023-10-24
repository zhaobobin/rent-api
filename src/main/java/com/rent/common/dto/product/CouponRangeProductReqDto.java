package com.rent.common.dto.product;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author boan
 */
@Data
@Schema(description = "商品审核参数")
public class CouponRangeProductReqDto extends Page implements Serializable {

    @Schema(description = "类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用")
    private String type;
    @Schema(description = "对应的类型的值，多个用逗号分割开")
    private String value;

    @Schema(description = "类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用")
    private String excludeType;
    @Schema(description = "对应的类型的值，多个用逗号分割开")
    private String excludeValue;


}
