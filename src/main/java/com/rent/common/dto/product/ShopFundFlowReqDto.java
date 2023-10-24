package com.rent.common.dto.product;

import com.rent.common.dto.Page;
import com.rent.common.enums.product.EnumShopFundOperate;
import com.rent.common.enums.product.EnumShopFundStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopFundFlowReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shopId;

    private String shopName;

    private EnumShopFundOperate operate;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
