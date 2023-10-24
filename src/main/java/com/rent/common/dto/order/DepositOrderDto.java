package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumOrderPayDepositStatus;
import com.rent.common.enums.order.EnumOrderStatus;
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
public class DepositOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;

    private String productImgSrc;

    private String productName;

    private BigDecimal paidDeposit;

    private BigDecimal unPaidDeposit;

    private Date createTime;

    private EnumOrderPayDepositStatus status;

    private EnumOrderStatus orderStatus;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
