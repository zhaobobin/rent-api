package com.rent.common.dto.product;

import lombok.Data;

@Data
public class OpeProductReqDto {
    private String itemId;
    private Integer tabId;
    private Integer indexSort;
    private String name;
    private String shopName;
}
