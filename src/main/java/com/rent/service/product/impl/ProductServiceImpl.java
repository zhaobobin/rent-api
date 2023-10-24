package com.rent.service.product.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.cache.product.ProductSortScoreCache;
import com.rent.common.constant.CategoryType;
import com.rent.common.constant.ProductKeys;
import com.rent.common.constant.RedisKey;
import com.rent.common.converter.product.ProductConverter;
import com.rent.common.dto.api.ProductSearchDto;
import com.rent.common.dto.backstage.OnShelfProductDto;
import com.rent.common.dto.backstage.OnShelfProductReqDto;
import com.rent.common.dto.backstage.UpdateExamineProductDto;
import com.rent.common.dto.backstage.UpdateProductHiddenReqDto;
import com.rent.common.dto.product.*;
import com.rent.common.enums.marketing.CouponRangeConstant;
import com.rent.common.enums.product.*;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.BeanUtils;
import com.rent.common.util.CheckDataUtils;
import com.rent.dao.product.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.*;
import com.rent.service.components.OrderCenterProductService;
import com.rent.service.product.*;
import com.rent.service.user.DistrictService;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2021-11-25 下午 1:39:45
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final OpeCategoryProductDao opeCategoryProductDao;
    private final OpeCategoryDao opeCategoryDao;
    private final ProductImageService productImageService;
    private final ProductCyclePricesService productCyclePricesService;
    private final ShopDao shopDao;
    private final OpeCategoryService opeCategoryService;
    private final OpeCategoryProductService opeCategoryProductService;
    private final ProductSkusService productSkusService;
    private final ProductSpecValueService productSpecValueService;
    private final ProductAdditionalServicesService productAdditionalServicesService;
    private final ProductGiveBackAddressesService productGiveBackAddressesService;
    private final ProductSpecService productSpecService;
    private final ProductLabelService productLabelService;
    private final ProductParameterService productParameterService;
    private final ProductSnapshotsService productSnapshotsService;
    private final OpeCustomProductService opeCustomProductService;
    private final ShopService shopService;
    private final OrderCenterProductService orderCenterProductService;
    private final DistrictService districtService;
    private final CategoryService categoryService;
    private final ProductImageDao productImageDao;
    private final OpeCustomProductDao opeCustomProductDao;
    private final ProductLabelDao productLabelDao;

    @Override
    public Product getByProductId(String productId) {
        Product product = productDao.getOne(new QueryWrapper<Product>().eq("product_id", productId)
                .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                .eq("status", ProductStatus.STATUS.getCode())
                .isNull("delete_time")
                .orderByDesc("create_time")
                .last("limit 0,1"));
        if (product == null) {
            throw new HzsxBizException("-1","商品不存在");
        }
        return product;
    }

    @Override
    @Transactional
    public Boolean busInsertProduct(ShopProductAddReqDto model) {
        shopService.checkShop(model.getShopId());
        Product product = new Product();
        product.setCategoryId(model.getCategoryId());
        product.setOldNewDegree(model.getOldNewDegree());
        product.setMinRentCycle(model.getMinRentCycle());
        product.setMaxRentCycle(model.getMaxRentCycle());
        product.setShopId(model.getShopId());
        product.setType(model.getType());
        product.setName(model.getName());
        product.setDetail(model.getDetail());
        product.setProvince(model.getProvince());
        product.setCity(model.getCity());
        product.setStatus(EnumProductStatus.VALID);
        product.setFreightType(model.getFreightType());
        product.setReturnRule(null == model.getReturnRule() ? ProductStatus.RETURN_RULE_ADVANCE.getCode() : model.getReturnRule());
        product.setProductId(String.valueOf(System.currentTimeMillis()));
        product.setBuyOutSupport(
                null == model.getBuyOutSupport() ? ProductStatus.IS_NOT_BUY_OUT.getCode() : model.getBuyOutSupport());
        product.setReturnFreightType(StringUtil.isEmpty(model.getReturnfreightType()) ? ProductFreightType.IS_FREE_TYPE.getCode() : model.getReturnfreightType());
        try {
            //修复租赁时间
            repaidProduct(model, product);
            this.insertProduct(product);
            //商品类目
            this.opeCategoryProductService.addCategoryProductCommon(product, product.getCategoryId());
            if (CollectionUtils.isNotEmpty(model.getShopAdditionals())) {
                this.productAdditionalServicesService.insertAdditionalService(product.getProductId(),
                        model.getShopAdditionals());
            }
            if (model.getAddIds() != null) {
                this.productGiveBackAddressesService.insertGiveBackAddress(product.getProductId(), model.getAddIds());
            }
            //新增规格和sku
            List<String> specId = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            //新增规格和sku
            this.insertProduct(product, model);
            //新增排序主图
            if (CollectionUtils.isNotEmpty(model.getImages())) {
                productImageService.insertProductImagesSort(product.getProductId(), model.getImages());
            }
            if (CollectionUtils.isNotEmpty(model.getLabels())) {
                productLabelService.batchProduct(product.getProductId(), model.getLabels());
            }
            if (CollectionUtils.isNotEmpty(model.getParameters())) {
                productParameterService.batchinsert(product.getProductId(), model.getParameters());
            }
            //商品快照
            this.productSnapshotsService.insertProductSnap(product.getProductId());
            //添加商品的时候-同步商品信息到小程序订单中心

            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("ProductServiceImpl: busInsertProduct 业务异常:" + e.getMessage());
            CheckDataUtils.dataError(e.getMessage());
        }
        return Boolean.FALSE;
    }

    @Override
    public ProductShopCateReqDto selectProductCateByProductId(String productId) {
        ProductShopCateReqDto productShopCateReqDto = new ProductShopCateReqDto();
        Product product = productDao.getOne(new QueryWrapper<Product>().eq("product_id", productId)
                .orderByDesc("create_time"));
        if (product == null) {
            return null;
        }
        productShopCateReqDto.setName(product.getName());
        productShopCateReqDto.setShopId(product.getShopId());
        OpeCategoryProduct opeCategoryProduct = this.opeCategoryProductDao.getOne(new QueryWrapper<OpeCategoryProduct>()
                .eq("item_id", productId)
                .orderByDesc("id")
                .last("limit 0,1")
        );
        if (null != opeCategoryProduct) {
            OpeCategory category = opeCategoryDao.getById(opeCategoryProduct.getOperateCategoryId());
            OpeCategory twoCategory = new OpeCategory();
            if(null != category && CategoryType.THREE_CATEGORY.equals(category.getType())){
                productShopCateReqDto.setThreeCategoryId(category.getId());
                productShopCateReqDto.setThreeCategoryName(category.getName());
                twoCategory = opeCategoryDao.getById(category.getParentId());
            }else {
                twoCategory = category;
            }
            productShopCateReqDto.setTwoCategoryId(twoCategory.getId());
            productShopCateReqDto.setCategoryId(Optional.ofNullable(twoCategory.getParentId())
                    .orElse(0));
            productShopCateReqDto.setTwoCategoryName(Optional.ofNullable(twoCategory.getName())
                    .orElse(""));
            OpeCategory oneCategory = opeCategoryDao.getById(twoCategory.getParentId());
            if (null != oneCategory) {
                productShopCateReqDto.setCategoryName(Optional.ofNullable(oneCategory.getName())
                        .orElse(""));
                productShopCateReqDto.setZfbCode(oneCategory.getZfbCode());
                productShopCateReqDto.setAntChainProductClassEnum(oneCategory.getAntChainCode());
            }
        }
        productShopCateReqDto.setProductId(product.getProductId());
        List<ProductImageAddReqDto> images = this.productImageService.selectProductImageByItemId(productId);
        if (CollectionUtils.isNotEmpty(images)) {
            productShopCateReqDto.setImages(images);
        }
        return productShopCateReqDto;
    }

    @Override
    public List<ListProductDto> getHotRentProduct(List<String> productIds) {
        List<Product> products = productDao.list(new QueryWrapper<Product>()
                .select("name", "product_id", "min_rent_cycle","old_new_degree")
                .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                .eq("status", ProductStatus.STATUS.getCode())
                .eq("shop_status", ProductStatus.SHOP_STATUS.getCode())
                .in("product_id",productIds)
                .eq("hidden",Boolean.FALSE)
                .orderByDesc("sort_score")
                .isNull("delete_time")
                .last("limit 20"));
        return packListProductDto(products);
    }

    @Override
    public Page<ProductDto> selectBusinessProduct(ShopProductSerachReqDto model) {
        String createStart = null;
        String createEnd = null;
        List<Integer> cateIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(model.getCreatTime())) {
            createStart = model.getCreatTime().get(0);
            createEnd = model.getCreatTime().get(1);
        }
        if(StringUtil.isNotEmpty(model.getCategoryIds())){
            cateIds = opeCategoryService.getCateIds(Integer.valueOf(model.getCategoryIds()), null);
        }
        Page<Product> productPage = this.productDao.page(new Page<Product>(model.getPageNumber(), model.getPageSize()),
                new QueryWrapper<Product>().select("id", "product_id", "`name`", "shop_id", "`status`", "audit_state",
                                "create_time", "update_time", "category_id", "type")
                        .eq(model.getType() != null, "type", model.getType())
                        .eq(model.getShopId() != null, "shop_id", model.getShopId())
                        .eq(StringUtils.isNotEmpty(model.getProductId()), "product_id", model.getProductId())
                        .eq(model.getAuditState() != null, "audit_state", model.getAuditState())
                        .isNull(model.getIsDelete()!= null && !model.getIsDelete() ,"delete_time")
                        .isNotNull(model.getIsDelete()!= null && model.getIsDelete(),"delete_time")
                        .like(StringUtils.isNotEmpty(model.getProductName()), "`name`", model.getProductName())
                        .between(StringUtils.isNotEmpty(createStart), "create_time", createStart, createEnd)
                        .in(CollectionUtils.isNotEmpty(cateIds), "category_id", cateIds)
                        .orderByDesc("update_time"));

        List<ProductDto> records = ProductConverter.modelList2DtoList(productPage.getRecords());
        if (CollectionUtils.isNotEmpty(records)) {
            records = records.stream()
                    .map(item -> {
                        item.setShopName(Optional.ofNullable(this.shopDao.getOne(new QueryWrapper<Shop>().select("name")
                                        .eq("shop_id", item.getShopId())
                                        .isNull("delete_time")))
                                .map(Shop::getName)
                                .orElse(null));
                        OpeCategoryNameAndIdDto opeCategoryNameAndIdDto = opeCategoryProductService.opeCategoryStrV1(item.getCategoryId());
                        item.setOpeCategoryStr(StringUtils.join(opeCategoryNameAndIdDto.getName(), "/"));
                        List<ProductImageAddReqDto> images = this.productImageService.selectProductImageByItemId(
                                item.getProductId());
                        if (CollectionUtils.isNotEmpty(images)) {
                            item.setSrc(images.get(0)
                                    .getSrc());
                        }
                        return item;
                    })
                    .collect(Collectors.toList());
        }
        return new Page<ProductDto>(productPage.getCurrent(), productPage.getSize(), productPage.getTotal()).setRecords(
                records);
    }

    @Override
    public ConfirmOrderProductDto getConfirmData(Long skuId, Integer days) {
        ConfirmOrderProductDto dto = new ConfirmOrderProductDto();
        ProductSkusDto productSkusDto = productSkusService.getSkuBySkuId(skuId);
        List<String> specName = productSpecValueService.getSpecName(skuId.intValue());
        List<ProductAdditionalServicesDto> additionalServices =  productAdditionalServicesService.queryProductAdditionalServicesByProductId(productSkusDto.getItemId());
        ProductCyclePrices skuCyclePrice = productCyclePricesService.getSkuCyclePrice(skuId,days);

        String productId = productSkusDto.getItemId();
        String  mainImage = productImageDao.getMainImage(productId);
        Product product = this.getByProductId(productId);
        dto.setSkuCyclePrice(skuCyclePrice.getPrice());
        dto.setSkuCycleSalePrice(skuCyclePrice.getSalePrice());
        dto.setProductSkus(productSkusDto);
        dto.setSpecName(specName);
        dto.setAdditionalServices(additionalServices);
        dto.setProductFreightType(product.getFreightType());
        dto.setBuyOutSupport(product.getBuyOutSupport());
        dto.setShopId(product.getShopId());
        dto.setProductName(product.getName());
        dto.setMainImage(mainImage);
        dto.setIsOnLine(product.getIsOnLine());
        return dto;
    }

    @Override
    public ShopProductAddReqDto selectProductEdit(Integer id) {
        ShopProductAddReqDto shopProductAddReqDto = new ShopProductAddReqDto();
        Product product = productDao.getById(id);
        if (product == null) {
            throw new HzsxBizException("-1","商品不存在");
        }
        shopProductAddReqDto.setCategoryId(product.getCategoryId());
        shopProductAddReqDto.setDetail(product.getDetail());
        shopProductAddReqDto.setProductId(product.getProductId());
        shopProductAddReqDto.setOldNewDegree(product.getOldNewDegree());
        shopProductAddReqDto.setMaxRentCycle(product.getMaxRentCycle());
        shopProductAddReqDto.setMinRentCycle(product.getMinRentCycle());
        shopProductAddReqDto.setId(id);
        shopProductAddReqDto.setType(product.getType());
        shopProductAddReqDto.setName(product.getName());
        shopProductAddReqDto.setShopId(product.getShopId());
        shopProductAddReqDto.setFreightType(product.getFreightType());
        shopProductAddReqDto.setReturnfreightType(product.getReturnFreightType());
        shopProductAddReqDto.setCity(product.getCity());
        shopProductAddReqDto.setProvince(product.getProvince());
        shopProductAddReqDto.setBuyOutSupport(product.getBuyOutSupport());
        shopProductAddReqDto.setReturnRule(null == product.getReturnRule() ? ProductStatus.RETURN_RULE_ADVANCE.getCode() : product.getReturnRule());
        shopProductAddReqDto.setImages(productImageService.selectProductImageByItemId(product.getProductId()));
        shopProductAddReqDto.setAddIds(productGiveBackAddressesService.getAddIds(product.getProductId()));
        shopProductAddReqDto.setProductSkuses(productSkusService.selectPruductSkusByItemId(product.getProductId()));
        shopProductAddReqDto.setSpecs(productSpecService.queryspecs(product.getProductId()));
        shopProductAddReqDto.setLabels(productLabelService.getProductLabelList(product.getProductId()));
        shopProductAddReqDto.setParameters(productParameterService.queryProductParameterList(product.getProductId()));
        shopProductAddReqDto.setShopAdditionals(
                productAdditionalServicesService.queryShopAdditionals(product.getProductId()));
        return shopProductAddReqDto;
    }

    @Override
    public Boolean updateBusProduct(ShopProductAddReqDto model) {
        Integer maxAd = model.getMaxRentCycle();
        Product product = productDao.getById(model.getId());
        String src = null;
        if (null == product) {
            throw new HzsxBizException("-1","商品不存在！！！");
        }
        product.setUpdateTime(new Date());
        product.setCategoryId(model.getCategoryId());
        product.setOldNewDegree(model.getOldNewDegree());
        product.setMinRentCycle(model.getMinRentCycle());
        product.setMaxRentCycle(model.getMaxRentCycle());
        product.setMaxAdvancedDays(maxAd);
        product.setShopId(model.getShopId());
        product.setType(model.getType());
        product.setCity(model.getCity());
        product.setProvince(model.getProvince());
        product.setBuyOutSupport(
                null == model.getBuyOutSupport() ? ProductStatus.IS_NOT_BUY_OUT.getCode() : model.getBuyOutSupport());
        product.setName(model.getName());
        product.setDetail(model.getDetail());
        product.setAuditState(EnumProductAuditState.PENDING);
        product.setId(model.getId());
        product.setFreightType(model.getFreightType());
        product.setReturnRule(null == model.getReturnRule() ? ProductStatus.RETURN_RULE_ADVANCE.getCode() : model.getReturnRule());
        product.setStatus(product.getType().equals(EnumProductType.ON_SHELF) ? EnumProductStatus.VALID : EnumProductStatus.INVALID);
        product.setReturnFreightType(StringUtil.isEmpty(model.getReturnfreightType()) ? ProductFreightType.IS_FREE_TYPE.getCode() : model.getReturnfreightType());
        try {
            //修复租赁时间
            repaidProduct(model, product);
            //规格库存周期价格图片
            productImageService.deleteProductImage(product.getProductId());
            productSkusService.deleteProductSkus(product.getProductId());
            productSpecService.deleteSpec(product.getProductId());
            productCyclePricesService.deleteCyclePrices(product.getProductId());
            productLabelService.deleteLabelProduct(product.getProductId());
            productParameterService.deleteParamsProduct(product.getProductId());
            productAdditionalServicesService.updateAdditionalService(product.getProductId(),model.getShopAdditionals());
            //归还地址
            if (CollectionUtils.isNotEmpty(model.getAddIds())) {
                productGiveBackAddressesService.updateGiveBackAddress(product.getProductId(), model.getAddIds());
            }
            //新增规格和sku
            this.insertProduct(product, model);

            //新增排序主图
            if (CollectionUtils.isNotEmpty(model.getImages())) {
                this.productImageService.insertProductImagesSort(product.getProductId(), model.getImages());
            }
            if (CollectionUtils.isNotEmpty(model.getLabels())) {
                productLabelService.batchProduct(product.getProductId(), model.getLabels());
            }
            this.productDao.updateById(product);
            if (CollectionUtils.isNotEmpty(model.getParameters())) {
                AsyncUtil.runAsync(() -> productParameterService.batchinsert(product.getProductId(), model.getParameters()));
            }
            AsyncUtil.runAsync(() -> productSnapshotsService.insertProductSnap(product.getProductId()));
            //修复绑定相关商品
            AsyncUtil.runAsync(() -> opeCategoryProductService.changeOpeCategoryProductStatus(product, model.getCategoryId(),
                    ProductStatus.NO_STATUS.getCode(), null));
            AsyncUtil.runAsync( () -> opeCustomProductService.repairCusProduct(ProductStatus.NO_STATUS.getCode(), product, null));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Boolean.TRUE;
    }

    /**
     * 将查询出来的商品信息封装成列表信息
     * @param records
     * @return
     */
    private List<ListProductDto> packListProductDto(List<Product> records){
        List<ListProductDto> dtoList = new ArrayList<>(records.size());
        if (CollectionUtils.isNotEmpty(records)) {
            for (Product product : records) {
                ListProductDto dto = new ListProductDto();
                List<ProductImageAddReqDto> images = productImageService.selectProductImageByItemId(product.getProductId());
                if (CollectionUtils.isNotEmpty(images)) {
                    dto.setImage(images.get(0).getSrc());
                }
                ProductCyclePricesDto productCyclePricesDto = this.productCyclePricesService.getLowestProductCyclePricesByItemId(product.getProductId());
                dto.setMinPrice(null != productCyclePricesDto.getPrice() ? productCyclePricesDto.getPrice() : BigDecimal.ZERO);
                dto.setName(product.getName());
                dto.setMinRentCycle(product.getMinRentCycle());
                dto.setProductId(product.getProductId());
                dto.setOldNewDegree(null != product.getOldNewDegree() ? product.getOldNewDegree().toString() : "0");
                dto.setSalesVolume(ProductSalesCache.getProductSales(product.getProductId()));
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public void repaidProduct(ShopProductAddReqDto model, Product product) {
        if (CollectionUtils.isNotEmpty(model.getProductSkuses())) {
            List<ShopProductSkuAllReqDto> productSkus = model.getProductSkuses();
            List<ProductCyclePricesDto> cycs = new ArrayList<>();
            productSkus.forEach(item -> {
                cycs.addAll(item.getCycs());
            });
            if (CollectionUtils.isNotEmpty(cycs)) {
                Integer minRentCycle = cycs.stream()
                        .mapToInt(ProductCyclePricesDto::getDays)
                        .min()
                        .getAsInt();
                Integer maxRentCycle = cycs.stream()
                        .mapToInt(ProductCyclePricesDto::getDays)
                        .max()
                        .getAsInt();
                BigDecimal sale = cycs.stream()
                        .map(ProductCyclePricesDto::getPrice)
                        .min((x1, x2) -> x1.compareTo(x2))
                        .get();
                product.setMinRentCycle(minRentCycle);
                product.setMaxRentCycle(maxRentCycle);
                product.setSale(sale);
            }
        }
    }

    public void insertProduct(Product product, ShopProductAddReqDto model) {
        List<ShopProductSkuAllReqDto> skus = model.getProductSkuses();
        //新增规格和sku
        if (CollectionUtils.isNotEmpty(skus)) {
            skus.forEach(skuAllReqDto -> {
                ProductSkusDto sku = new ProductSkusDto();
                sku.setItemId(product.getProductId());
                sku.setCreateTime(new Date());
                sku.setMarketPrice(skuAllReqDto.getMarketPrice());
                sku.setInventory(skuAllReqDto.getInventory());
                sku.setOldNewDegree(model.getOldNewDegree());
                sku.setTotalInventory(skuAllReqDto.getInventory());
                sku.setDepositPrice(skuAllReqDto.getDepositPrice());
                //设置买断价格
                if (null != model.getBuyOutSupport()) {
                    sku.setBuyOutSupport(model.getBuyOutSupport());
                }
                Integer skuId = productSkusService.addProductSkus(sku);
                List<ProductCyclePricesDto> cycs = skuAllReqDto.getCycs();
                productCyclePricesService.insertCyclePrices(cycs, skuId, product.getProductId());
                if (CollectionUtils.isNotEmpty(skuAllReqDto.getSpecAll())) {
                    //录入商品规格和库存规格
                    this.productSpecService.insertSpec(product.getProductId(), skuId, skuAllReqDto.getSpecAll());
                }
            });
        }
    }

    public void insertProduct(Product product) {
        Date date = new Date();
        product.setCreateTime(date);
        product.setUpdateTime(date);
        product.setShopStatus(ProductStatus.SHOP_STATUS.getCode());
        product.setStatus(product.getType().equals(EnumProductType.ON_SHELF) ? EnumProductStatus.VALID : EnumProductStatus.INVALID);
        product.setAuditState(EnumProductAuditState.PENDING);
        productDao.save(product);
    }

    @Override
    public Page<ShopProductAddReqDto> searchProduct(ProductSearchDto searchDto) {
        Integer minRentCycleDays = searchDto.getMinRentCycleDays();
        String content = searchDto.getContent();
        List<ShopProductAddReqDto> result = new ArrayList<>();
        try {
            Page<Product> productPage = this.productDao.page(
                    new Page<Product>(searchDto.getPageNumber(), searchDto.getPageSize()),
                    new QueryWrapper<Product>().select("id", "create_time", "name", "product_id", "shop_id","old_new_degree",
                                    "min_rent_cycle", "sales_volume")
                            .like("name", content)
                            .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                            .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                            .eq("status", ProductStatus.STATUS.getCode())
                            .eq("hidden",Boolean.FALSE)
                            .eq("shop_status", ProductStatus.SHOP_STATUS.getCode())
                            .eq(StringUtils.isNotEmpty(searchDto.getShopId()),"shop_id",searchDto.getShopId())
                            .eq(minRentCycleDays != null && 0 != minRentCycleDays, "min_rent_cycle",searchDto.getMinRentCycleDays())
                            .isNull("delete_time")
                            .orderByDesc("sort_score"));
            List<ProductDto> records = ProductConverter.modelList2DtoList(productPage.getRecords());
            if (CollectionUtils.isNotEmpty(records)) {
                result = records.stream()
                        .map(item -> {
                            ShopProductAddReqDto shopProductAddReqDto = new ShopProductAddReqDto();
                            shopProductAddReqDto.setName(item.getName());
                            shopProductAddReqDto.setProductId(item.getProductId());
                            shopProductAddReqDto.setSalesVolume(item.getSalesVolume());
                            shopProductAddReqDto.setMinRentCycle(item.getMinRentCycle());
                            shopProductAddReqDto.setOldNewDegree(item.getOldNewDegree());
                            List<ProductImageAddReqDto> images = this.productImageService.selectProductImageByItemId(
                                    item.getProductId());
                            if (CollectionUtils.isNotEmpty(images)) {
                                shopProductAddReqDto.setSrc(images.get(0)
                                        .getSrc());
                                shopProductAddReqDto.setImages(images);
                            }
                            shopProductAddReqDto.setLabels(
                                    this.productLabelService.getProductLabelList(item.getProductId()));
                            ProductCyclePricesDto productCyclePricesDto
                                    = this.productCyclePricesService.getLowestProductCyclePricesByItemId(item.getProductId());
                            shopProductAddReqDto.setLowestSalePrice(productCyclePricesDto.getPrice());
                            return shopProductAddReqDto;
                        })
                        .collect(Collectors.toList());
            }
            return new Page<ShopProductAddReqDto>(productPage.getCurrent(), productPage.getSize(),
                    productPage.getTotal()).setRecords(result);
        } catch (Exception e) {
            log.error("ProductServiceImpl: searchProduct 业务异常:" + e.getMessage());
            CheckDataUtils.dataError("ProductServiceImpl: searchProduct 业务异常:" + e.getMessage());
        }
        return new Page<>(1, searchDto.getPageSize());
    }

    @Override
    public ShopProductAddReqDto getProductDetailLite(String productId) {
        ShopProductAddReqDto shopProductAddReqDto = new ShopProductAddReqDto();
        Product product = productDao.getOne(new QueryWrapper<Product>().eq("product_id", productId)
                .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                .eq("status", ProductStatus.STATUS.getCode())
                .isNull("delete_time")
                .orderByDesc("create_time")
                .last("limit 0,1"));
        if (product == null) {
            CheckDataUtils.dataError(productId + "商品不存在");
        }
        String cityname = districtService.getNameByDistrictId(product.getCity());
        shopProductAddReqDto.setCategoryId(product.getCategoryId());
        shopProductAddReqDto.setDetail(product.getDetail());
        shopProductAddReqDto.setProductId(product.getProductId());
        shopProductAddReqDto.setOldNewDegree(product.getOldNewDegree());
        shopProductAddReqDto.setMaxRentCycle(product.getMaxRentCycle());
        shopProductAddReqDto.setMinRentCycle(product.getMinRentCycle());
        shopProductAddReqDto.setSalesVolume(product.getSalesVolume());
        shopProductAddReqDto.setFreightType(product.getFreightType());
        shopProductAddReqDto.setCity(cityname);
        shopProductAddReqDto.setId(product.getId());
        shopProductAddReqDto.setType(product.getType());
        shopProductAddReqDto.setName(product.getName());
        shopProductAddReqDto.setShopId(product.getShopId());
        shopProductAddReqDto.setBuyOutSupport(product.getBuyOutSupport());
        shopProductAddReqDto.setReturnRule(product.getReturnRule());
        shopProductAddReqDto.setReturnfreightType(StringUtil.isEmpty(product.getReturnFreightType()) ? ProductFreightType.IS_FREE_TYPE.getCode() : product.getReturnFreightType());
        shopProductAddReqDto.setImages(productImageService.selectProductImageByItemId(product.getProductId()));
        shopProductAddReqDto.setProductSkuses(productSkusService.selectPruductSkusByItemId(product.getProductId()));
        shopProductAddReqDto.setLabels(productLabelService.getProductLabelList(product.getProductId()));
        shopProductAddReqDto.setParameters(productParameterService.queryProductParameterList(product.getProductId()));
        shopProductAddReqDto.setSpecs(productSpecService.queryspecs(product.getProductId()));
        ProductCyclePricesDto productCyclePricesDto
                = this.productCyclePricesService.getLowestProductCyclePricesByItemId(product.getProductId());
        shopProductAddReqDto.setLowestSalePrice(
                null != productCyclePricesDto ? productCyclePricesDto.getPrice() : new BigDecimal(0));
        shopProductAddReqDto.setServiceTel(shopService.selectShopServiceTelInfo(product.getShopId()));
        return shopProductAddReqDto;
    }

    @Override
    public List<ProductDetailRecommendResp> recommendLite(String productId) {
        List<ProductDetailRecommendResp> recommendList = new ArrayList();
        Product product = this.productDao.getOne(new QueryWrapper<Product>().select("category_id,shop_id")
                .eq("product_id", productId));
        if (null != product) {
            List<Product> products = this.productDao.list(new QueryWrapper<Product>().select(
                            "product_id,name,sales_volume,old_new_degree,shop_id")
                    .eq("status", ProductStatus.STATUS.getCode())
                    .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                    .eq("category_id", product.getCategoryId())
                    .eq("shop_id", product.getShopId())
                    .eq("hidden",Boolean.FALSE)
                    .isNull("delete_time")
                    .orderByDesc("sort_score")
                    .last("limit 35"));
            for (Product recommendProduct : products) {
                if((!recommendProduct.getProductId().equals(productId))){
                    ProductDetailRecommendResp recommendResp = new ProductDetailRecommendResp();
                    recommendResp.setProductId(recommendProduct.getProductId());
                    recommendResp.setName(recommendProduct.getName());
                    recommendResp.setSalesVolume(recommendProduct.getOldNewDegree());
                    recommendResp.setOldNewDegree(recommendProduct.getOldNewDegree());
                    recommendList.add(recommendResp);
                }
                if(recommendList.size()>=8){
                    break;
                }
            }
            recommendList = recommendList.stream()
                    .map(item -> {
                        List<ProductImageAddReqDto> images = this.productImageService.selectProductImageByItemId(item.getProductId());
                        if (CollectionUtils.isNotEmpty(images)) {
                            item.setImageSrc(images.get(0).getSrc());
                        }
                        ProductCyclePricesDto productCyclePricesDto= this.productCyclePricesService.getLowestProductCyclePricesByItemId(item.getProductId());
                        item.setLowestSalePrice(null != productCyclePricesDto ? productCyclePricesDto.getPrice() : new BigDecimal(0));
                        return item;
                    }).collect(Collectors.toList());
        }
        return recommendList;
    }

    @Override
    public List<ListProductDto> getProductInByIds(List<String> productIds) {
        List<Product> records = productDao.list(new QueryWrapper<Product>()
                .select("name", "product_id", "min_rent_cycle","old_new_degree")
                .in("product_id",productIds)
                .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                .eq("status", ProductStatus.STATUS.getCode())
                .eq("shop_status", ProductStatus.SHOP_STATUS.getCode())
                .isNull("delete_time"));
        return packListProductDto(records);
    }

    @Override
    public List<ListProductDto> getProductInByIdsV1(List<String> productIds) {
        List<Product> records = productDao.list(new QueryWrapper<Product>()
                .select("name", "product_id", "min_rent_cycle","old_new_degree")
                .in("product_id",productIds)
                .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                .eq("status", ProductStatus.STATUS.getCode())
                .eq("shop_status", ProductStatus.SHOP_STATUS.getCode())
                .isNull("delete_time"));
        List<ListProductDto> dto = packListProductDto(records);
        Map<String,List<String>> labelsMap = productLabelDao.getProductLabelList(productIds);
        for(ListProductDto listProductDto : dto){
            listProductDto.setLabels(labelsMap.get(listProductDto.getProductId()));
        }
        return dto;
    }

    @Override
    public Page<ListProductDto> getLiteProductInRange(CouponRangeProductReqDto request) {
        String type = request.getType();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (CouponRangeConstant.RANGE_TYPE_CATEGORY.equals(type)) {
            //分类下的商品
            String[] parentCategoryIds = request.getValue().split(",");
            List<String> productIds = opeCategoryProductDao.getProductIdInParentId(parentCategoryIds);
            queryWrapper.in("product_id", productIds);
        } else if (CouponRangeConstant.RANGE_TYPE_PRODUCT.equals(type)) {
            //按照商品ID
            queryWrapper.in("product_id", request.getValue().split(","));
        }
        Page<Product> page = productDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                queryWrapper.select("name", "product_id", "min_rent_cycle","old_new_degree")
                        .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                        .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                        .eq("status", ProductStatus.STATUS.getCode())
                        .eq("shop_status", ProductStatus.SHOP_STATUS.getCode())
                        .isNull("delete_time"));
        List<ListProductDto> dtoList = packListProductDto(page.getRecords());
        return new Page<ListProductDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(dtoList);
    }

    @Override
    public ShopProductAddReqDto selectProductDetailByProductId(String productId) {
        ShopProductAddReqDto shopProductAddReqDto = new ShopProductAddReqDto();
        Product product = productDao.getOne(new QueryWrapper<Product>().eq("product_id", productId)
                .eq("audit_state", ProductStatus.AUDIT_PASS_STATS.getCode())
                .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                .eq("status", ProductStatus.STATUS.getCode())
                .isNull("delete_time")
                .orderByDesc("create_time")
                .last("limit 0,1"));
        if (product == null) {
            CheckDataUtils.dataError(productId + "商品不存在");
        }
        String cityname = districtService.getNameByDistrictId(product.getCity());
        shopProductAddReqDto.setCategoryId(product.getCategoryId());
        if (null != product.getCategoryId()){
            Integer oneCategoryId = opeCategoryDao.getOneCategoryId(product.getCategoryId());
            shopProductAddReqDto.setOneCategoryId(oneCategoryId);
        }
        shopProductAddReqDto.setDetail(product.getDetail());
        shopProductAddReqDto.setProductId(product.getProductId());
        shopProductAddReqDto.setOldNewDegree(product.getOldNewDegree());
        shopProductAddReqDto.setMaxRentCycle(product.getMaxRentCycle());
        shopProductAddReqDto.setMinRentCycle(product.getMinRentCycle());
        Integer salesVolume = 0;
        if (null != product.getSalesVolume() && 0 != product.getSalesVolume()) {
            salesVolume = Math.multiplyExact(product.getSalesVolume(), 11);
        }
        shopProductAddReqDto.setSalesVolume(salesVolume);
        shopProductAddReqDto.setFreightType(product.getFreightType());
        shopProductAddReqDto.setReturnfreightType(StringUtil.isEmpty(product.getReturnFreightType()) ? ProductFreightType.IS_FREE_TYPE.getCode() : product.getReturnFreightType());
        shopProductAddReqDto.setCity(cityname);
        shopProductAddReqDto.setId(product.getId());
        shopProductAddReqDto.setType(product.getType());
        shopProductAddReqDto.setName(product.getName());
        shopProductAddReqDto.setShopId(product.getShopId());
        shopProductAddReqDto.setBuyOutSupport(product.getBuyOutSupport());
        shopProductAddReqDto.setImages(productImageService.selectProductImageByItemId(product.getProductId()));
        shopProductAddReqDto.setAddIds(productGiveBackAddressesService.getAddIds(product.getProductId()));
        shopProductAddReqDto.setProductSkuses(productSkusService.selectPruductSkusByItemId(product.getProductId()));
        shopProductAddReqDto.setLabels(productLabelService.getProductLabelList(product.getProductId()));
        shopProductAddReqDto.setParameters(productParameterService.queryProductParameterList(product.getProductId()));
        shopProductAddReqDto.setSpecs(productSpecService.queryspecs(product.getProductId()));
        ProductCyclePricesDto productCyclePricesDto
                = this.productCyclePricesService.getLowestProductCyclePricesByItemId(product.getProductId());
        shopProductAddReqDto.setLowestSalePrice(
                null != productCyclePricesDto ? productCyclePricesDto.getPrice() : new BigDecimal(0));
        shopProductAddReqDto.setAdditionals(
                productAdditionalServicesService.queryProductAdditionalServicesByProductId(product.getProductId()));
        shopProductAddReqDto.setServiceTel(shopService.selectShopServiceTelInfo(product.getShopId()));
        return shopProductAddReqDto;
    }

    @Override
    public Map<String, String> getProductName(List<String> productIds) {
        Map<String, String> params = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(productIds)) {
            List<Product> products = this.productDao.list(new QueryWrapper<Product>().select("name", "product_id")
                    .in("product_id", productIds).orderByDesc("update_time"));
            if (CollectionUtils.isNotEmpty(products)) {
                products.forEach(item -> {
                    params.put(item.getProductId(), item.getName());
                });
            }
        }
        return params;
    }

    @Override
    public List<ProductDto> selectProductByName(String name) {
        List<Product> productList = this.productDao.list(new QueryWrapper<Product>().like("name", name)
                .isNull("delete_time"));
        return ProductConverter.modelList2DtoList(productList);
    }

    @Override
    public String getReturnFreightType(String productId) {
        Product product = this.productDao.getOne(new QueryWrapper<Product>().isNull("delete_time").eq("product_id", productId));
        if (product == null) {
            CheckDataUtils.dataError(productId + "商品不存在");
        }
        return StringUtil.isEmpty(product.getReturnFreightType()) ? ProductFreightType.IS_FREE_TYPE.getCode() : product.getReturnFreightType();
    }

    @Override
    public Boolean busUpdateProduct(Integer id, Integer type, Integer status) {
        Product product = productDao.getById(id);
        product.setUpdateTime(new Date());
        product.setType(EnumProductType.find(type));
        product.setStatus(EnumProductStatus.find(status));
        productDao.updateById(product);
        this.opeCategoryProductService.changeOpeCategoryProductStatus(product, product.getCategoryId(), status, null);
        return true;
    }

    @Override
    @Transactional
    public Boolean busUpdateProductByRecycleDel(Integer id) {
        Product product = productDao.getById(id);
        product.setUpdateTime(new Date());
        product.setDeleteTime(new Date());
        productDao.updateById(product);
        this.opeCategoryProductService.deleteProduct(product.getCategoryId(), product.getProductId());
        this.opeCustomProductService.deleteProduct(product.getProductId());
        return true;
    }

    @Override
    public JSONObject selectShopProductListAndShopV1(String shopId, Integer pageNumber, Integer pageSize) {
        CheckDataUtils.judgeNull(shopId, pageNumber, pageSize);
        Page<ProductAndDirectProductDto> page = new Page<>(pageNumber, pageSize);
        page.setCurrent(pageNumber);
        String jedisIndexKey = RedisKey.ALIPAY_SHOP_PRODUCTS_V1 + shopId + pageNumber + pageSize;
        Object shopProductCach = RedisUtil.get(jedisIndexKey);
        List<ProductAndDirectProductDto> productAndDirectProductDtos = new ArrayList<>();
        pageNumber = (pageNumber - 1) * pageSize;
        try {
            if (shopProductCach == null) {
                List<Map<String, Object>> maps = this.productDao.selectShopProductListAndShop(shopId, pageNumber, pageSize);
                if (CollectionUtils.isNotEmpty(maps)) {
                    try {
                        productAndDirectProductDtos = BeanUtils.mapsToObjectsV1(maps, ProductAndDirectProductDto.class);
                        productAndDirectProductDtos = productAndDirectProductDtos.stream().map(item -> {
                                    List<ProductImageAddReqDto> images = this.productImageService.selectProductImageByItemId(item.getProductId());
                                    if (CollectionUtils.isNotEmpty(images)) {
                                        item.setSrc(images.get(0).getSrc());
                                    }
                                    if(StringUtil.isNotEmpty(item.getProductId())  && item.getProductId().contains(CategoryType.PRODUCT_DIRECT)){
                                        item.setLowestSalePrice(item.getSale());
                                    }else{
                                        ProductCyclePricesDto productCyclePricesDto = this.productCyclePricesService.getLowestProductCyclePricesByItemId(item.getProductId());
                                        item.setLowestSalePrice(productCyclePricesDto.getPrice());
                                    }
                                    return item;
                                }
                        ).collect(Collectors.toList());
                        page.setTotal(this.productDao.countShopProductListAndShop(shopId));
                    } catch (Exception e) {
                        page.setTotal(0);
                    }
                }
                page.setRecords(productAndDirectProductDtos);
                Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>().select("service_tel", "name", "logo",
                                "background")
                        .eq("shop_id", shopId)
                        .isNull("delete_time")
                        .last("limit 0,1"));
                JSONObject ob = new JSONObject();
                ob.put("shop", shop);
                ob.put("productPage", page);
                RedisUtil.set(jedisIndexKey, JSON.toJSONString(ob), 3000);
                return ob;
            } else {
                return JSONObject.parseObject(shopProductCach.toString());
            }
        } catch (Exception e) {
            log.error("ShopServiceImpl: selectShopProductList 业务异常:" + e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Object> selectExamineProductEdit(String productId) {
        Map<String, Object> map = new HashMap<>();
        CheckDataUtils.judgeNull(productId);
        Product product = this.productDao.getOne(new QueryWrapper<Product>().select("id", "name", "product_id",
                        "category_id", "shop_id", "status", "type", "min_rent_cycle", "max_rent_cycle", "min_advanced_days",
                        "max_advanced_days"
                ).eq("product_id", productId)
                .isNull("delete_time"));
        if (product == null) {
            CheckDataUtils.dataError("商品不存在");
        }
        map.put("product", product);
        map.put("category", categoryService.categoryStrV1(product.getCategoryId(), product.getProductId()));
        List<ProductImage> productImages = this.productImageDao.list(new QueryWrapper<ProductImage>().select("id",
                        "src", "is_main")
                .eq("product_id", productId)
                .isNull("delete_time"));
        map.put("productImages", productImages);
        return map;
    }

    @Override
    public Boolean updateExamineProduct(UpdateExamineProductDto model) {
        String productId = model.getProductId();
        Integer id = model.getId();
        CheckDataUtils.judgeNull(productId, id);
        String src = null;
        Product product = this.productDao.getById(id);
        if (Objects.isNull(product)) {
            CheckDataUtils.dataError("商品不存在请仔细核实");
        }
        if (!product.getAuditState().equals(EnumProductAuditState.PASS)) {
            CheckDataUtils.dataError("请先审核商品");
        }
        product.setStatus(product.getType().equals(EnumProductType.ON_SHELF) ? EnumProductStatus.VALID : EnumProductStatus.INVALID);
        org.springframework.beans.BeanUtils.copyProperties(model, product);
        Date now = new Date();
        List<String> images = model.getImages();
        if (CollectionUtils.isNotEmpty(images)) {
            // 删除以前的照片
            productImageDao.list(new QueryWrapper<ProductImage>().select("id")
                            .eq("product_id", productId)
                            .isNull("delete_time"))
                    .parallelStream()
                    .forEach(productImage -> {
                        productImage.setDeleteTime(now);
                        productImageDao.updateById(productImage);
                    });
            src = model.getImages().get(0);
            productImageService.insertProductImages(images, productId);
        }
        RedisUtil.del(ProductKeys.PRODUCT_DETAIL + productId);
        product.setUpdateTime(now);
        this.productDao.updateById(product);
        ProductDto dto = ProductConverter.model2Dto(product);
        dto.setSrc(src);
        if (Objects.equals(model.getType(),EnumProductType.ON_SHELF.getCode())) {
            opeCategoryProductService.changeOpeCategoryProductStatus(product, model.getCategoryId(),
                    ProductStatus.STATUS.getCode(), null);
            AsyncUtil.runAsync(
                    () -> opeCustomProductService.repairCusProduct(ProductStatus.STATUS.getCode(), product, null));
        } else if (Objects.equals(model.getType(),EnumProductType.OFF_SHELF.getCode())) {
            opeCategoryProductService.changeOpeCategoryProductStatus(product, model.getCategoryId(),
                    ProductStatus.NO_STATUS.getCode(), null);
            AsyncUtil.runAsync(
                    () -> opeCustomProductService.repairCusProduct(ProductStatus.NO_STATUS.getCode(), product, null));
        }
        return true;
    }


    @Override
    public Boolean initProductScore(List<OnShelfProductReqDto> request) {
        ProductSortScoreCache.cleanSysScore();
        productDao.cleanSortScore();
        opeCustomProductDao.cleanSortScore();
        opeCategoryProductDao.cleanSortScore();
        for (OnShelfProductReqDto dto : request) {
            String productId = dto.getProductId();
            Integer addScore = ProductSortScoreCache.getAddScore(productId);
            Integer score = dto.getSysScore();
            if(addScore!=null){
                score = score + addScore;
            }
            productDao.setProductSortScore(dto.getProductId(),score);
            opeCustomProductDao.setProductSortScore(dto.getProductId(),score);
            opeCategoryProductDao.setProductSortScore(dto.getProductId(),score);
            ProductSortScoreCache.setSysScore(dto.getProductId(),score);
        }

        List<String> list = productDao.getSortScoreZeroProductIds();
        for (String productId : list) {
            Integer addScore = ProductSortScoreCache.getAddScore(productId);
            if(addScore!=null){
                productDao.setProductSortScore(productId,addScore);
                opeCustomProductDao.setProductSortScore(productId,addScore);
                opeCategoryProductDao.setProductSortScore(productId,addScore);
                ProductSortScoreCache.setSysScore(productId,addScore);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateProductHidden(UpdateProductHiddenReqDto reqDto) {
        productDao.updateProductHidden(reqDto.getHidden(),reqDto.getProductIds());
        return Boolean.TRUE;
    }

    @Override
    public Page<OnShelfProductDto> queryOnShelfProduct(OnShelfProductReqDto request) {
        List<String> shopNameShopIdList = null;
        if (StringUtils.isNotBlank(request.getShopName())) {
            List<Shop> shopList = this.shopDao.list(new QueryWrapper<Shop>().select("shop_id")
                    .like("`name`", request.getShopName())
                    .isNull("delete_time"));
            shopNameShopIdList = shopList.stream().map(Shop::getShopId).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(shopNameShopIdList)){
                return new Page<>(1, request.getPageSize());
            }
        }
        Page<Product> productPage = this.productDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<Product>().select("id", "product_id", "`name`", "shop_id","sale","sort_score","hidden")
                        .in(CollectionUtils.isNotEmpty(shopNameShopIdList), "shop_id", shopNameShopIdList)
                        .eq(StringUtils.isNotEmpty(request.getProductId()), "product_id", request.getProductId())
                        .like(StringUtils.isNotEmpty(request.getName()), "`name`", request.getName())
                        .eq(StringUtils.isNotBlank(request.getShopId()), "shop_id", request.getShopId())
                        .eq("`status`", 1).eq("type", 1).eq("audit_state",2)
                        .eq("hidden",request.getHidden())
                        .isNull("delete_time")
                        .orderByDesc("sort_score"));
        List<OnShelfProductDto> records = new ArrayList<>();
        for (Product product : productPage.getRecords()) {
            OnShelfProductDto dto = new OnShelfProductDto();
            dto.setShopId(product.getShopId());
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setCategoryId(product.getCategoryId());
            dto.setSale(product.getSale());
            Shop shop = shopDao.getOne(new QueryWrapper<Shop>().select("name").eq("shop_id", product.getShopId()).isNull("delete_time"));
            dto.setShopName(Optional.ofNullable(shop).map(Shop::getName).orElse(null));
            dto.setHidden(product.getHidden());

            JSONObject addScoreInfo = ProductSortScoreCache.getAddScoreInfo(product.getProductId());
            Integer addScore = 0;
            if(addScoreInfo!=null){
                addScore = addScoreInfo.getInteger(ProductSortScoreCache.addScoreKey);
                dto.setAddSortScoreExpire(addScoreInfo.getDate(ProductSortScoreCache.expireTimeKey));
            }
            dto.setSort(ProductSortScoreCache.getRank(product.getProductId()));
            OpeCategoryNameAndIdDto opeCategoryNameAndIdDto = opeCategoryProductService.opeCategoryStr(product.getProductId());
            if (null != opeCategoryNameAndIdDto) {
                dto.setOpeCategoryStr(StringUtils.join(opeCategoryNameAndIdDto.getName(), "/"));
            }
            dto.setAddSortScore(product.getSortScore());
            dto.setSysSortScore(product.getSortScore()-addScore);
            records.add(dto);
        }
        return new Page<OnShelfProductDto>(productPage.getCurrent(), productPage.getSize(), productPage.getTotal()).setRecords(records);
    }

    @Override
    public Boolean addScore(OnShelfProductReqDto request) {
        Integer finalScore = request.getAddScore();
        Integer originFinalScore = productDao.updateProductSortScore(request.getProductId(), finalScore);

        Integer originAddScore = ProductSortScoreCache.getAddScore(request.getProductId());
        originAddScore = originAddScore == null ? 0 : originAddScore;
        Integer originSysScore = originFinalScore - originAddScore;
        Integer addScore = request.getAddScore() -  originSysScore;

        ProductSortScoreCache.setAddSortScore(request.getProductId(),addScore,request.getExpireTime());
        opeCustomProductDao.setProductSortScore(request.getProductId(),finalScore);
        opeCategoryProductDao.setProductSortScore(request.getProductId(),finalScore);
        ProductSortScoreCache.setSysScore(request.getProductId(),finalScore);
        return Boolean.TRUE;
    }

    @Override
    public Page<AbleProductDto> ableProductV1(String keyWord, Integer pageNumber, Integer pageSize,String shopId) {
        Page<Product> productPage = productDao.page(new Page<>(pageNumber, pageSize),
                new QueryWrapper<Product>()
                        .select("id", "name", "product_id")
                        .eq(StringUtils.isNotEmpty(shopId),"shop_id",shopId)
                        .eq("type", ProductStatus.PUT_ON_SHELF.getCode())
                        .eq("status", ProductStatus.STATUS.getCode())
                        .eq("shop_status", ProductStatus.SHOP_STATUS.getCode())
                        .isNull("delete_time")
                        .like(StringUtils.isNotEmpty(keyWord),"name", keyWord)
                        .or().like(StringUtils.isNotEmpty(keyWord),"product_id", keyWord)
        );
        List<AbleProductDto> result = null;
        if (CollectionUtils.isNotEmpty(productPage.getRecords())) {
            result = productPage.getRecords().stream().map(item -> {
                AbleProductDto dto = new AbleProductDto();
                dto.setProductName(item.getName());
                dto.setProductId(item.getProductId());
                return dto;
            }).collect(Collectors.toList());
        }else {
            result = Collections.EMPTY_LIST;
        }
        return new Page<AbleProductDto>(productPage.getCurrent(), productPage.getSize(), productPage.getTotal()).setRecords(result);
    }


}
