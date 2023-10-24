package com.rent.common.dto.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 小程序列表页面的商品信息
 * @author youruo
 * @Date 2020-06-16 15:06
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productId;

    private String name;

    private String image;

    private Integer minRentCycle;

    private BigDecimal minPrice;

    private String oldNewDegree;

    private Integer salesVolume;

    private Integer collectNum;

    private List<String> labels;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
