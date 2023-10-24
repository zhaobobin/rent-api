package com.rent.common.dto.components.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Builder
@Schema(description = "查询蚁盾分请求参数")
@NoArgsConstructor
@AllArgsConstructor
public class QueryAntChainShieldRequest implements Serializable {

    private static final long serialVersionUID = -348646664117061184L;

    private String orderId;
    private String orderCreateTime;
    private String uid;
    private String ip;
    private String mobile;
    private String idCard;
    private String userName;
    private String aliPayUserId;
    private String deliverAddress;
    private String longitude;
    private String latitude;

}
