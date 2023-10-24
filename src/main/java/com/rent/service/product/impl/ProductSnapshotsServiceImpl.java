package com.rent.service.product.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.*;
import com.rent.common.dto.product.ProductSnapshotsDto;
import com.rent.common.dto.product.ShopProductSnapResponse;
import com.rent.common.dto.product.ShopProductSnapSkusResponse;
import com.rent.common.dto.product.ShopProductSnapSpecResponse;
import com.rent.dao.product.*;
import com.rent.model.product.*;
import com.rent.service.product.ProductSnapshotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品快照表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSnapshotsServiceImpl implements ProductSnapshotsService {

    private final ProductSnapshotsDao productSnapshotsDao;
    private final ProductDao productDao;
    private final ShopDao shopDao;
    private final ProductImageDao productImageDao;
    private final ProductAdditionalServicesDao productAdditionalServicesDao;
    private final ProductGiveBackAddressesDao productGiveBackAddressesDao;
    private final ProductSkusDao productSkusDao;
    private final ProductSpecDao productSpecDao;
    private final ProductCyclePricesDao productCyclePricesDao;
    private final ProductSkuValuesDao productSkuValuesDao;
    private final ProductSpecValueDao productSpecValueDao;

    @Override
    public void insertProductSnap(String itemId) {
        ShopProductSnapResponse shopProductSnapResponse = new ShopProductSnapResponse();
        Product product = productDao.getOne(new QueryWrapper<Product>().eq("product_id", itemId));

        shopProductSnapResponse.setProduct(ProductConverter.model2Dto(product));

        Shop shop = shopDao.getOne(new QueryWrapper<Shop>().eq("shop_id", product.getShopId())
            .isNull("delete_time"));
        shopProductSnapResponse.setShop(ShopConverter.model2Dto(shop));

        List<ProductImage> productImage = productImageDao.list(new QueryWrapper<ProductImage>().eq("product_id", itemId)
            .isNull("delete_time"));
        shopProductSnapResponse.setProductImage(ProductImageConverter.modelList2DtoList(productImage));

        List<ProductAdditionalServices> additionalServices = productAdditionalServicesDao.list(
            new QueryWrapper<ProductAdditionalServices>().eq("product_id", itemId)
                .isNull("delete_time"));
        shopProductSnapResponse.setProductAdditionalServices(
            ProductAdditionalServicesConverter.modelList2DtoList(additionalServices));

        List<ProductGiveBackAddresses> giveBackAddresses = productGiveBackAddressesDao.list(
            new QueryWrapper<ProductGiveBackAddresses>().eq("item_id", itemId)
                .isNull("delete_time"));
        shopProductSnapResponse.setProductGiveBackAddresses(
            ProductGiveBackAddressesConverter.modelList2DtoList(giveBackAddresses));

        List<ProductSkus> productSkuses = productSkusDao.list(new QueryWrapper<ProductSkus>().eq("item_id", itemId)
            .isNull("delete_time"));

        List<ProductSpec> productSpecs = productSpecDao.list(new QueryWrapper<ProductSpec>().eq("item_id", itemId)
            .isNull("delete_time"));

        List<ShopProductSnapSkusResponse> shopSnapSkus = new ArrayList<>();

        for (int i = 0; i < productSkuses.size(); i++) {
            ShopProductSnapSkusResponse shopProductSnapSku = new ShopProductSnapSkusResponse();
            shopProductSnapSku.setProductSkus(ProductSkusConverter.model2Dto(productSkuses.get(i)));
            //商品价格数组
            List<ProductCyclePrices> cyclePricess = productCyclePricesDao.list(
                new QueryWrapper<ProductCyclePrices>().eq("item_id", itemId)
                    .eq("sku_id", productSkuses.get(i)
                        .getId()));
            shopProductSnapSku.setCyclePrices(ProductCyclePricesConverter.modelList2DtoList(cyclePricess));

            List<ProductSkuValues> skuValuesLists = productSkuValuesDao.list(
                new QueryWrapper<ProductSkuValues>().eq("sku_id", productSkuses.get(i)
                    .getId())
                    .isNull("delete_time"));
            shopProductSnapSku.setSkuValues(ProductSkuValuesConverter.modelList2DtoList(skuValuesLists));
            shopSnapSkus.add(shopProductSnapSku);

        }
        shopProductSnapResponse.setShopProductSnapSkus(shopSnapSkus);

        List<ShopProductSnapSpecResponse> shopProductSnapSpecs = new ArrayList<>();
        for (int i = 0; i < productSpecs.size(); i++) {
            ShopProductSnapSpecResponse shopProductSnapSpec = new ShopProductSnapSpecResponse();
            shopProductSnapSpec.setProductSpec(ProductSpecConverter.model2Dto(productSpecs.get(i)));
            List<ProductSpecValue> preSpecValue = productSpecValueDao.list(
                new QueryWrapper<ProductSpecValue>().eq("product_spec_id", productSpecs.get(i)
                    .getId())
                    .isNull("delete_time"));
            List<ShopProductSnapSpecResponse.ShopProductSnapSpecValue> shopProductSnapSpecValues = new ArrayList<>();
            for (int j = 0; j < preSpecValue.size(); j++) {
                ShopProductSnapSpecResponse.ShopProductSnapSpecValue shopProductSnapSpecValue
                    = new ShopProductSnapSpecResponse.ShopProductSnapSpecValue();
                shopProductSnapSpecValue.setProductSpecValue(ProductSpecValueConverter.model2Dto(preSpecValue.get(j)));
                shopProductSnapSpecValues.add(shopProductSnapSpecValue);
            }
            shopProductSnapSpec.setSpecValues(shopProductSnapSpecValues);
            shopProductSnapSpecs.add(shopProductSnapSpec);

        }
        shopProductSnapResponse.setSnapSpecs(shopProductSnapSpecs);
        String jsonStr = JSON.toJSONString(shopProductSnapResponse);
        ProductSnapshots productSnapshots = new ProductSnapshots();
        productSnapshots.setShopId(shop.getShopId());
        productSnapshots.setItemId(product.getProductId());
        productSnapshots.setData(jsonStr);
        productSnapshots.setCreateTime(new Date());
        productSnapshots.setStatus(0);
        productSnapshots.setVersion(System.currentTimeMillis());
        productSnapshotsDao.save(productSnapshots);
    }

    @Override
    public List<ProductSnapshotsDto> queryProductSnapshotsList(List<Integer> ids) {
        List<ProductSnapshotsDto> reslut = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<ProductSnapshots> snaps = this.productSnapshotsDao.list(
                new QueryWrapper<ProductSnapshots>().in("id", ids)
                    .orderByDesc("version"));
            reslut = ProductSnapshotsConverter.modelList2DtoList(snaps);
        }
        return reslut;
    }

    @Override
    public Integer queryProductSnapshotsId(String productId) {
        ProductSnapshots productSnapshots = productSnapshotsDao.getOne(new QueryWrapper<ProductSnapshots>()
                .select("id")
                .eq("item_id",productId)
                .orderByDesc("version")
                .last("limit 0,1"));
        return productSnapshots.getId();
    }
}