package com.rent.common.dto.product;

import com.rent.common.enums.product.EnumShopFundOperate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopFundFlowDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String shopId;

    private String shopName;

    private EnumShopFundOperate operate;

    private String operator;

    private BigDecimal changeAmount;

    private BigDecimal beforeAmount;

    private BigDecimal afterAmount;

    private String flowNo;

    private String remark;

    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
