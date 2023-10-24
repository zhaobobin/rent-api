package com.rent.common.dto.product;

import com.rent.model.product.ShopAdditionalServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 三方增值服务返回
 *
 * @author youruo
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdditonalDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private List<ShopAdditionalServices> services;


    private Map<Integer, Integer> newestShopAdditionalMap;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
