package com.rent.service.export.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.export.ProductExportDto;
import com.rent.common.dto.product.OpeCategoryNameAndIdDto;
import com.rent.common.dto.product.ProductCyclePricesDto;
import com.rent.common.dto.product.ProductSkusDto;
import com.rent.common.dto.product.ShopProductSerachReqDto;
import com.rent.common.enums.export.ExportFileName;
import com.rent.config.annotation.ExportFile;
import com.rent.dao.product.ProductDao;
import com.rent.dao.product.ShopDao;
import com.rent.model.product.Product;
import com.rent.model.product.Shop;
import com.rent.service.export.ProductExportService;
import com.rent.service.product.OpeCategoryProductService;
import com.rent.service.product.ProductCyclePricesService;
import com.rent.service.product.ProductSkusService;
import com.rent.service.user.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductExportServiceImpl implements ProductExportService {

    private final ProductDao productDao;
    private final DistrictService districtService;
    private final ProductCyclePricesService productCyclePricesService;
    private final ProductSkusService productSkusService;
    private final ShopDao shopDao;
    private final OpeCategoryProductService opeCategoryProductService;

    @Override
    @ExportFile(fileName=ExportFileName.PRODUCT,exportDtoClazz=ProductExportDto.class)
    public List<ProductExportDto> exportBusinessProduct(ShopProductSerachReqDto model) {
        String createStart = null;
        String createEnd = null;
        if (CollectionUtils.isNotEmpty(model.getCreatTime())) {
            createStart = model.getCreatTime()
                    .get(0);
            createEnd = model.getCreatTime()
                    .get(1);
        }
        List<Product> productList = this.productDao.list(new QueryWrapper<Product>().select("id", "product_id",
                        "`name`", "shop_id", "`status`", "audit_state", "create_time", "update_time", "category_id", "type","province","city")
                .eq(null != model.getType(), "type", model.getType())
                .eq(StringUtils.isNotEmpty(model.getShopId()), "shop_id", model.getShopId())
                .eq(StringUtils.isNotEmpty(model.getProductId()), "product_id", model.getProductId())
                .eq(null != model.getAuditState(), "audit_state", model.getAuditState())
                .isNull("delete_time")
                .like(StringUtils.isNotEmpty(model.getProductName()), "`name`", model.getProductName())
                .between(StringUtils.isNotEmpty(createStart), "create_time", createStart, createEnd)
                .in(StringUtils.isNotEmpty(model.getCategoryIds()), "category_id", model.getCategoryIds())
                .orderByDesc("create_time")
        );
        return packExportInfo(productList);
    }

    private List<ProductExportDto> packExportInfo(List<Product> productList) {
        if(CollectionUtils.isEmpty(productList)){
            return Collections.EMPTY_LIST;
        }

        Set<String> districtIds = new HashSet<>();
        for (Product product : productList) {
            districtIds.add(product.getProvince());
            districtIds.add(product.getCity());
        }
        List<Shop> list = shopDao.list(new QueryWrapper<Shop>().select("shop_id,name"));
        Map<String,String> shopNameMap = list.stream().collect(Collectors.toMap(Shop::getShopId,Shop::getName));

        Map<String, String> distinctNameMap = districtService.getDistinctName(new ArrayList<>(districtIds));
        List<ProductExportDto> exportDtoList = new ArrayList<>(productList.size());
        if (CollectionUtils.isNotEmpty(productList)) {
            productList.forEach(item -> {
                ProductExportDto dto = new ProductExportDto();
                dto.setShopId(item.getShopId());
                dto.setShopName(shopNameMap.get(item.getShopId()));
                dto.setProductName(item.getName());
                dto.setProductId(item.getProductId());
                OpeCategoryNameAndIdDto opeCategoryNameAndIdDto = opeCategoryProductService.opeCategoryStr(item.getProductId());
                if (null != opeCategoryNameAndIdDto && null != opeCategoryNameAndIdDto.getName() && opeCategoryNameAndIdDto.getName().size() > 1) {
                    dto.setCategory(StringUtils.join(opeCategoryNameAndIdDto.getName(),"/"));
                } else {
                    dto.setCategory("-");
                }
                dto.setCreateTime(item.getCreateTime());
                ProductCyclePricesDto productCyclePricesDto = productCyclePricesService.getLowestProductCyclePricesByItemId(item.getProductId());
                ProductSkusDto skusDto = productSkusService.getSkuBySkuId(productCyclePricesDto.getSkuId());
                dto.setMinRent(productCyclePricesDto.getPrice());
                dto.setMarketPrice(skusDto.getMarketPrice());
                dto.setSalePrice(productCyclePricesDto.getSalePrice());
                dto.setType(item.getType().getMsg());
                String province = distinctNameMap.get(item.getProvince());
                String city = distinctNameMap.get(item.getCity());
                dto.setDeliveryCity(province + "/" + city);
                exportDtoList.add(dto);
            });
        }
        return exportDtoList;
    }
}
