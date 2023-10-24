package com.rent.common.dto.order.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "押金充值记录")
public class DepositOrderRecordDto implements Serializable {

    private static final long serialVersionUID = 5970456924606668186L;

    /**
     * 充值记录列表
     */
    private List<DepositOrderDetailDto>  depositOrderDetailDtoList;
}
