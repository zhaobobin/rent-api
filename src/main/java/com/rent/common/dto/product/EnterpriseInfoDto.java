package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hzsx-rent-parent
 * @description: 企业店铺资质信息
 * @author: yr
 * @create: 2020-08-03 18:02
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商家店铺企业资质详情实体")
public class EnterpriseInfoDto {

    @Schema(description = "企业资质信息")
    private ShopEnterpriseInfosDto shopEnterpriseInfos;

    @Schema(description = "企业资质图片地址")
    private List<ShopEnterpriseCertificatesDto> shopEnterpriseCertificates = new ArrayList<>();


}
