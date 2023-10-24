package com.rent.common.dto.components.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "蚂蚁链同步物流信息")
public class AntChainSyncLogistic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    private String arriveConfirmUrl;
    private String arriveConfirmTime;
    private String logisticCompanyName;
    private String logisticsOrderId;
    private String deliverTime;

    private String leaseCorpName;
    private String leaseCorpId;
    private String leaseCorpOwnerName;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
