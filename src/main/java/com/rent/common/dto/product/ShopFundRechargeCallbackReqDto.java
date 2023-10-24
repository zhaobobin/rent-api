package com.rent.common.dto.product;

import com.rent.common.dto.Page;
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
public class ShopFundRechargeCallbackReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    private String outTradeNo;
    private String tradeNo;
    private String buyerLogonId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
