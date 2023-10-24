package com.rent.common.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableTabProductResp implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;


    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 店铺名称
     */
    private String shopName;
}
