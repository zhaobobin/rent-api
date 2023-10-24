package com.rent.common.dto.backstage.request;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "获取tab可以添加的商品列表 请求参数")
public class QueryAvailableTabProductReq extends Page implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "tabId",required = true)
    private Long tabId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品ID")
    private String productId;

    @Schema(description = "店铺名称")
    private String shopName;
}
