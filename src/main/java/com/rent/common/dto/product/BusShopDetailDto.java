package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 店铺资质表
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商家店铺详情实体")
public class BusShopDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * shop
     *
     */
    @Schema(description = "店铺信息")
    private ShopDto shop;



    /**
     * shopEnterpriseInfos
     *
     */
    @Schema(description = "企业信息")
    private EnterpriseInfoDto enterpriseInfoDto;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
