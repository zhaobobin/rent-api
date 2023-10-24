package com.rent.service.product.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rent.common.constant.CategoryType;
import com.rent.common.converter.product.*;
import com.rent.common.dto.order.OrderAdditionalServicesDto;
import com.rent.common.dto.product.*;
import com.rent.common.enums.product.ProductStatus;
import com.rent.dao.product.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.*;
import com.rent.service.product.ProductExtraService;
import com.rent.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-08-06 15:12
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductExtraServiceImpl implements ProductExtraService {

    private final ProductDao productDao;
    private final ShopDao shopDao;
    private final ProductService productService;
    private final ProductSnapshotsDao productSnapshotsDao;
    private final OpeCategoryProductDao opeCategoryProductDao;
    private final ShopAdditionalServicesDao shopAdditionalServicesDao;
    private final ProductAdditionalServicesDao productAdditionalServicesDao;
    private final ProductGiveBackAddressesDao productGiveBackAddressesDao;
    private final ShopGiveBackAddressesDao shopGiveBackAddressesDao;
    private final ProductSkusDao productSkusDao;
    private final ProductSkuValuesDao productSkuValuesDao;
    private final ProductSpecValueDao productSpecValueDao;
    private final ProductSpecDao productSpecDao;
    private final ProductCyclePricesDao productCyclePricesDao;
    private final ProductImageDao productImageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransferProductResponse saveTransferProduct(TransferProductDto request) {
        TransferProductResponse response = new TransferProductResponse();
        ShopProductSnapResponse shopProductSnapResponse = new ShopProductSnapResponse();
        Long transferredSnapShotId = request.getTransferredSnapShotId();
        Long transferredSkuId = request.getTransferredSkuId();
        List<OrderAdditionalServicesDto> orderAdditionalServicesDtos = request.getOrderAdditionalServicesDtos();
        String transferredProductId = request.getTransferredProductId();
        String newestProductId = CategoryType.PRODUCT_TRANSFER + System.currentTimeMillis();
        response.setNewestProductId(newestProductId);
        String transferShopId = request.getTransferShopId();
        ProductSnapshots productSnapshots = productSnapshotsDao.getById(transferredSnapShotId);
        if (null == productSnapshots) {
            throw new HzsxBizException("-1", "商品快照不存在");
        }
        Shop shop = shopDao.getByShopId(transferShopId);
        if (null == shop) {
            throw new HzsxBizException("-1", "接手商店不存在");
        }
        //归还地址
        ShopGiveBackAddresses shopGiveBackAddresses = shopGiveBackAddressesDao.getShopGiveBackAddressesByShopId(transferShopId);
        if (null == shopGiveBackAddresses) {
            throw new HzsxBizException("-1", "接手商店归还地址不存在");
        }
        Date now = new Date();
        //解析快照信息，录入商品信息
        ShopProductSnapResponse productSnapshot = JSONObject.parseObject((String) productSnapshots.getData(), ShopProductSnapResponse.class);
        ProductDto productDto = productSnapshot.getProduct();
        List<ProductImageDto> productImage = productSnapshot.getProductImage();
        List<ProductAdditionalServicesDto> productAdditionalServices = productSnapshot.getProductAdditionalServices();
        List<ShopProductSnapSkusResponse> shopProductSnapSkus = productSnapshot.getShopProductSnapSkus();
        List<ShopProductSnapSpecResponse> snapSpecs = productSnapshot.getSnapSpecs();
        if (CollectionUtil.isEmpty(shopProductSnapSkus)) {
            throw new HzsxBizException("-1", "转单商品sku有误");
        }
        //获取skuId最新的库存信息，后期新加的修改库存功能，不记录到快照中
        List<Integer> skuIds = shopProductSnapSkus.stream().map(a -> {
            return a.getProductSkus().getId();
        }).collect(Collectors.toList());
        Map<Integer, Integer> integerMap = productSkusDao.getInventoryById(skuIds);
        //商品主表
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.setReturnFreightType(productDto.getReturnfreightType());
        product.setShopId(shop.getShopId());
        product.setShopStatus(ProductStatus.SHOP_STATUS.getCode());
        product.setHidden(Boolean.TRUE);
        product.setCreateTime(now);
        product.setUpdateTime(now);
        product.setProductId(newestProductId);
        product.setSalesVolume(1);
        productDao.save(product);
        shopProductSnapResponse.setProduct(ProductConverter.model2Dto(product));
        shopProductSnapResponse.setShop(ShopConverter.model2Dto(shop));
        //录入商品图片信息
        if (CollectionUtil.isNotEmpty(productImage)) {
            List<ProductImage> images = productImageDao.saveBatchImange(productImage, newestProductId);
            shopProductSnapResponse.setProductImage(ProductImageConverter.modelList2DtoList(images));
        }
        //商品增值服务
        if (CollectionUtil.isNotEmpty(orderAdditionalServicesDtos) && CollectionUtil.isNotEmpty(productAdditionalServices)) {
            List<Integer> shopAdditionalServicesIds = productAdditionalServices.stream().map(ProductAdditionalServicesDto::getShopAdditionalServicesId).collect(Collectors.toList());
            //店铺增值服务
            AdditonalDto additonalDto = shopAdditionalServicesDao.saveBatchAdditional(shopAdditionalServicesIds, transferShopId, orderAdditionalServicesDtos);
            //商品增值服务
            List<ProductAdditionalServices> productAdditionals = productAdditionalServicesDao.batchSaveProductAdditional(productAdditionalServices, additonalDto.getServices(), newestProductId);
            response.setNewestShopAdditionalServicesId(additonalDto.getNewestShopAdditionalMap());
            shopProductSnapResponse.setProductAdditionalServices(ProductAdditionalServicesConverter.modelList2DtoList(productAdditionals));
        } else {
            shopProductSnapResponse.setProductAdditionalServices(Lists.newArrayList());
        }
        //商品归还地址录入
        ProductGiveBackAddresses productGiveBackAddresses = new ProductGiveBackAddresses();
        productGiveBackAddresses.setCreateTime(now);
        productGiveBackAddresses.setUpdateTime(now);
        productGiveBackAddresses.setItemId(newestProductId);
        productGiveBackAddresses.setAddressId(shopGiveBackAddresses.getId());
        productGiveBackAddressesDao.saveOrUpdate(productGiveBackAddresses);
        shopProductSnapResponse.setProductGiveBackAddresses(ProductGiveBackAddressesConverter.modelList2DtoListV1(productGiveBackAddresses));
        List<ShopProductSnapSkusResponse> shopSnapSkus = Lists.newArrayList();
        //录入规格信息--录入原来的product_spec_id和新增后的product_spec_id
        Map<Long, Long> data = Maps.newHashMap();
        if (CollectionUtil.isNotEmpty(snapSpecs)) {
            List<ShopProductSnapSpecResponse> shopSnapSpecs = Lists.newArrayList();
            snapSpecs.forEach(spec -> {
                ShopProductSnapSpecResponse shopProductSnapSpecResponse = new ShopProductSnapSpecResponse();
                ProductSpec productSpec = ProductSpecConverter.dto2Model(spec.getProductSpec());
                List<ShopProductSnapSpecResponse.ShopProductSnapSpecValue> specValues = spec.getSpecValues();
                List<ShopProductSnapSpecResponse.ShopProductSnapSpecValue> snapSpecValues = Lists.newArrayList();
                productSpec.setItemId(newestProductId);
                productSpec.setCreateTime(now);
                productSpecDao.save(productSpec);
                Long productSpecId = Long.valueOf(productSpec.getId());
                shopProductSnapSpecResponse.setProductSpec(ProductSpecConverter.model2Dto(productSpec));
                if (CollectionUtil.isNotEmpty(specValues)) {
                    specValues.stream().map((x) -> {
                        ProductSpecValueDto productSpecValue = x.getProductSpecValue();
                        Long orgId = productSpecValue.getId();
                        productSpecValue.setProductSpecId(productSpecId);
                        productSpecValue.setCreateTime(now);
                        x.setProductSpecValue(productSpecValue);
                        ProductSpecValue b = ProductSpecValueConverter.dto2Model(productSpecValue);
                        productSpecValueDao.save(b);
                        data.put(orgId, b.getId());
                        ShopProductSnapSpecResponse.ShopProductSnapSpecValue value = new ShopProductSnapSpecResponse.ShopProductSnapSpecValue();
                        value.setProductSpecValue(ProductSpecValueConverter.model2Dto(b));
                        snapSpecValues.add(value);
                        return x;
                    }).forEach(System.out::println);
                }
                shopProductSnapSpecResponse.setSpecValues(snapSpecValues);
                shopSnapSpecs.add(shopProductSnapSpecResponse);
            });
            shopProductSnapResponse.setSnapSpecs(shopSnapSpecs);
        }

        //商品Sku表
        shopProductSnapSkus.forEach(item -> {
            ShopProductSnapSkusResponse shopProductSnapSku = new ShopProductSnapSkusResponse();
            List<ProductCyclePricesDto> snaCyclePrices = Lists.newArrayList();
            List<ProductSkuValuesDto> snaSkuValues = Lists.newArrayList();
            ProductSkusDto productSkusDto = item.getProductSkus();
            Long orgSkuId = Long.valueOf(productSkusDto.getId());
            List<ProductCyclePricesDto> cyclePricesDtos = item.getCyclePrices();
            ProductSkus productSkus = new ProductSkus();
            BeanUtils.copyProperties(productSkusDto, productSkus);
            productSkus.setItemId(newestProductId);
            productSkus.setCreateTime(now);
            productSkus.setUpdateTime(now);
            //库存取最新的
            Integer inventory = null == integerMap.get(productSkusDto.getId()) ? 0 : integerMap.get(productSkusDto.getId());
            productSkus.setInventory(inventory);
            productSkus.setTotalInventory(inventory);
            productSkusDao.save(productSkus);
            shopProductSnapSku.setProductSkus(ProductSkusConverter.model2Dto(productSkus));
            Long skuId = Long.valueOf(productSkus.getId());
            if (orgSkuId.equals(transferredSkuId)) {
                response.setNewestSkuId(skuId);
            }
            List<ProductSkuValuesDto> skuValues = item.getSkuValues();
            //商品规格表
            skuValues.forEach(sku -> {
                ProductSkuValues productSkuValues = new ProductSkuValues();
                productSkuValues.setCreateTime(now);
                productSkuValues.setSpecValueId(data.get(sku.getSpecValueId()));
                productSkuValues.setSkuId(skuId);
                productSkuValuesDao.save(productSkuValues);
                snaSkuValues.add(ProductSkuValuesConverter.model2Dto(productSkuValues));
            });
            //商品周期价格
            cyclePricesDtos.forEach(cyc -> {
                ProductCyclePrices productCyclePrices = new ProductCyclePrices();
                BeanUtils.copyProperties(cyc, productCyclePrices);
                productCyclePrices.setSkuId(skuId);
                productCyclePrices.setItemId(newestProductId);
                productCyclePrices.setCreateTime(now);
                productCyclePrices.setUpdateTime(now);
                productCyclePricesDao.save(productCyclePrices);
                snaCyclePrices.add(ProductCyclePricesConverter.model2Dto(productCyclePrices));
            });
            shopProductSnapSku.setCyclePrices(snaCyclePrices);
            shopProductSnapSku.setSkuValues(snaSkuValues);
            shopSnapSkus.add(shopProductSnapSku);
        });
        shopProductSnapResponse.setShopProductSnapSkus(shopSnapSkus);
        ProductSnapshots snapshots = new ProductSnapshots();
        String jsonStr = JSON.toJSONString(shopProductSnapResponse);
        snapshots.setShopId(shop.getShopId());
        snapshots.setItemId(newestProductId);
        snapshots.setData(jsonStr);
        snapshots.setCreateTime(now);
        snapshots.setStatus(0);
        snapshots.setVersion(System.currentTimeMillis());
        //商品快照录入
        productSnapshotsDao.save(snapshots);
        response.setNewestSnapShotId(Long.valueOf(snapshots.getId()));
        //类目下商品复制
        opeCategoryProductDao.copyTransferCategoryProduct(transferredProductId, newestProductId, transferShopId);
        return response;
    }

    @Override
    public Boolean busCopyProduct(String productId) {
        Product product = this.productDao.getOne(new QueryWrapper<Product>()
                .eq("product_id", productId)
                .isNull("delete_time")
                .last("limit 1")
        );
        if (null == product) {
            throw new HzsxBizException("-1","商品不存在");
        }
        ShopProductAddReqDto model = productService.selectProductEdit(product.getId());
        model.setName(model.getName() + "【复制后】");
        productService.busInsertProduct(model);
        return Boolean.TRUE;
    }
}
