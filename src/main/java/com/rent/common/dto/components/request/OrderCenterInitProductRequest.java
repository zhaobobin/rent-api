package com.rent.common.dto.components.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author udo
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCenterInitProductRequest implements Serializable {

    /**
     * 商品ID
     */
    String productId;

    /**
     * 商品图片在oss中的key
     */
    String objectKey;

    /**
     * 商品名称
     */
    String productName;

    /**
     * 商品价格
     */
    Long price;

    /**
     * 商品价格
     */
    String channelId;
}
