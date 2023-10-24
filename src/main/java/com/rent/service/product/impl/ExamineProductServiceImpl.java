
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.cache.product.ProductSwipingCache;
import com.rent.common.constant.ProductKeys;
import com.rent.common.converter.product.ProductConverter;
import com.rent.common.converter.product.ProductImageConverter;
import com.rent.common.dto.product.*;
import com.rent.common.enums.product.EnumProductAuditState;
import com.rent.common.enums.product.EnumProductStatus;
import com.rent.common.enums.product.EnumProductType;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.CheckDataUtils;
import com.rent.dao.product.*;
import com.rent.dao.user.DistrictDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.*;
import com.rent.service.product.*;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台商品类目表Service
 *
 * @author youruo
 * @Date 2020-06-15 10:57
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ExamineProductServiceImpl implements ExamineProductService {

    private final ShopDao shopDao;
    private final OpeCategoryProductDao opeCategoryProductDao;
    private final ProductDao productDao;
    private final OpeCategoryProductService opeCategoryProductService;
    private final ProductCyclePricesService productCyclePricesService;
    private final ShopService shopService;
    private final ProductImageDao productImageDao;
    private final CategoryService categoryService;
    private final PlatformServiceMarksDao platformServiceMarksDao;
    private final ProductSnapshotsService productSnapshotsService;
    private final OpeCustomProductService opeCustomProductService;
    private final ProductAuditLogDao productAuditLogDao;
    private final ProductLabelService productLabelService;
    private final ProductParameterService productParameterService;
    private final DistrictDao districtDao;
    private final ProductSpecDao productSpecDao;
    private final ProductSpecValueDao productSpecValueDao;
    private final ProductSkusService productSkusService;
    private final ProductServiceMarksDao productServiceMarksDao;
    private final ProductGiveBackAddressesService productGiveBackAddressesService;
    private final ProductAdditionalServicesService productAdditionalServicesService;


    @Override
    public Page<ProductDto> selectExaminePoroductList(ExamineProductQueryReqDto model) {
        String createStart = null;
        String createEnd = null;

        if (CollectionUtils.isNotEmpty(model.getCreatTime())) {
            createStart = model.getCreatTime()
                    .get(0);
            createEnd = model.getCreatTime()
                    .get(1);
        }
        String shopName = model.getShopName();
        List<String> shopNameShopIdList = null;
        List<String> itemIdList = null;
        if (StringUtils.isNotBlank(shopName) && CollectionUtils.isEmpty(shopNameShopIdList = this.shopDao.list(
                        new QueryWrapper<Shop>().select("shop_id")
                                .like(StringUtils.isNotEmpty(shopName), "`name`", shopName)
                                .isNull("delete_time"))
                .stream()
                .map(Shop::getShopId)
                .collect(Collectors.toList()))) {
            return new Page<ProductDto>(1, model.getPageSize());
        }
        if (null != model.getCategoryId()) {
            itemIdList = this.opeCategoryProductDao.list(new QueryWrapper<OpeCategoryProduct>().select("item_id").isNull("delete_time")
                            .eq("`operate_category_id`", model.getCategoryId())
                            .or().eq("parent_category_id", model.getCategoryId()))
                    .stream()
                    .map(OpeCategoryProduct::getItemId)
                    .collect(Collectors.toList());
        }
        String shopId = model.getShopId();
        Page<Product> productPage = this.productDao.page(new Page<Product>(model.getPageNumber(), model.getPageSize()),
                new QueryWrapper<Product>().select("id", "product_id", "`name`", "shop_id", "`status`", "audit_state",
                                "create_time", "update_time", "type", "sale")
                        .in(CollectionUtils.isNotEmpty(shopNameShopIdList), "shop_id", shopNameShopIdList)
                        .in(CollectionUtils.isNotEmpty(itemIdList), "item_id", itemIdList)
                        .like(StringUtils.isNotEmpty(model.getProductId()), "product_id", model.getProductId())
                        .like(StringUtils.isNotEmpty(model.getProductName()), "`name`", model.getProductName())
                        .eq(StringUtils.isNotEmpty(model.getAuditState()), "audit_state", model.getAuditState())
                        .like(StringUtils.isNotBlank(shopId), "shop_id", shopId)
                        .between(CollectionUtils.isNotEmpty(model.getCreatTime()), "create_time", createStart, createEnd)
                        .eq(model.getStatus() != null, "`status`", model.getStatus())
                        .eq(model.getType() != null, "type", model.getType())
                        .isNull("delete_time")
                        .orderByDesc("update_time"));

        List<ProductDto> records = ProductConverter.modelList2DtoList(productPage.getRecords());
        if (CollectionUtils.isNotEmpty(records)) {
            for (ProductDto product : records) {
                product.setShopName(Optional.ofNullable(this.shopDao.getOne(new QueryWrapper<Shop>().select("name")
                                .eq("shop_id", product.getShopId())
                                .isNull("delete_time")))
                        .map(Shop::getName)
                        .orElse(null));
                OpeCategoryNameAndIdDto opeCategoryNameAndIdDto = opeCategoryProductService.opeCategoryStr(product.getProductId());
                if (null != opeCategoryNameAndIdDto) {
                    product.setOpeCategoryStr(StringUtils.join(opeCategoryNameAndIdDto.getName(), "/"));
                }
                ProductCyclePricesDto productCyclePricesDto
                        = this.productCyclePricesService.getLowestProductCyclePricesByItemId(product.getProductId());
                if (null != productCyclePricesDto) {
                    product.setSale(productCyclePricesDto.getPrice());
                }

            }
        }
        return new Page<ProductDto>(productPage.getCurrent(), productPage.getSize(), productPage.getTotal()).setRecords(
                records);
    }


    @Override
    public ExamineProductResponse selectExamineProductDetailV1(String productId) {
        ExamineProductResponse detailDTO = new ExamineProductResponse();
        try {
            Product product = this.productDao.getOne(new QueryWrapper<Product>().select("id", "create_time", "name",
                            "product_id", "category_id", "shop_id", "detail", "province", "city", "status", "type", "audit_state",
                            "rent_rule_id", "compensate_rule_id", "min_rent_cycle", "max_rent_cycle", "min_advanced_days",
                            "max_advanced_days", "old_new_degree", "sales_volume", "monthly_sales_volume", "freight_type",
                            "audit_refuse_reason", "buy_out_support","return_rule","return_freight_type","is_on_line")
                    .eq("product_id", productId)
                    .isNull("delete_time"));
            //校验
            if (product == null) {
                log.warn("Goods have been deleted");
                throw new HzsxBizException("-1", "商品不存在呀~");
            }

            // 类目信息
            detailDTO.setCategoryStr(categoryService.categoryStrV1(product.getCategoryId(), product.getProductId()));
            // 店铺信息
            detailDTO.setShop(shopService.queryShopDetail(ShopReqDto.builder()
                    .shopId(product.getShopId())
                    .build()));
            // 商品基础信息
            detailDTO.setId(product.getId());
            detailDTO.setMaxRentCycle(product.getMaxRentCycle());
            detailDTO.setMinRentCycle(product.getMinRentCycle());
            detailDTO.setName(product.getName());
            detailDTO.setItemId(product.getProductId());
            detailDTO.setCreateTime(product.getCreateTime());
            detailDTO.setSalesVolume(product.getSalesVolume());
            detailDTO.setAuditState(product.getAuditState());
            detailDTO.setMonthlySalesVolume(product.getMonthlySalesVolume());
            detailDTO.setType(product.getType());
            detailDTO.setOldNewDegree(product.getOldNewDegree());
            detailDTO.setDetail(product.getDetail());
            detailDTO.setCity(districtDao.getNameByDistrictId(product.getCity()));
            detailDTO.setFreightType(product.getFreightType());
            detailDTO.setReturnfreightType(product.getReturnFreightType());
            detailDTO.setIsOnLine(product.getIsOnLine());
            if (StringUtils.isNotEmpty(product.getAuditRefuseReason())){
                detailDTO.setAuditRefuseReason(product.getAuditRefuseReason());
            }

            // 商品图片
            List<ProductImage> productImages = this.productImageDao.list(new QueryWrapper<ProductImage>().select("id",
                            "src", "is_main")
                    .eq("product_id", productId)
                    .isNull("delete_time"));
            detailDTO.setImages(ProductImageConverter.modelList2DtoList(productImages));
            //规格信息
            List<Map> productSpecList = productSpecDao.selectByItemId(productId);
            List<SpecsResponse> specsdtoList = new ArrayList<>();
            for (Map map : productSpecList) {
                Number num = (Number) map.get("id");
                Integer id = num.intValue();
                String name = map.get("name") == null ? "" : map.get("name")
                        .toString();
                List<ProductSpecValueDto> productSpecValue = this.productSpecValueDao.selectBySpecId(id);
                specsdtoList.add(SpecsResponse.builder()
                        .id(id)
                        .name(name)
                        .values(productSpecValue)
                        .build());
            }
            detailDTO.setSpecs(specsdtoList);
            detailDTO.setPricingInformations(this.productSkusService.selectPricingByItemId(productId));
            //审核详情返回是否支持买断
            detailDTO.setBuyOutSupport(product.getBuyOutSupport());
            //归还规则
            detailDTO.setReturnRule(null == product.getReturnRule() ? ProductStatus.RETURN_RULE_ADVANCE.getCode():product.getReturnRule());
            //平台服务信息
            List<ProductServiceMarks> productServiceMarks = this.productServiceMarksDao.list(
                    new QueryWrapper<ProductServiceMarks>().eq("item_id", productId)
                            .isNull("delete_time"));

            List<Integer> integerList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(productServiceMarks)) {
                for (ProductServiceMarks productServiceMark : productServiceMarks) {
                    integerList.add(productServiceMark.getInfoId());
                }
            }
            List<PlatformServiceMarks> platformServiceMarks = null;
            if (CollectionUtils.isNotEmpty(integerList)) {
                platformServiceMarks = this.platformServiceMarksDao.list(
                        new QueryWrapper<PlatformServiceMarks>().in("id", integerList)
                                .isNull("delete_time"));
            }
            //物流费
            detailDTO.setFreightType(product.getFreightType());

            List<ShopGiveBackAddressesDto> add = productGiveBackAddressesService.getGiveBack(product.getProductId());
            if (CollectionUtils.isNotEmpty(add)) {
                detailDTO.setShopGiveBackAddressesList(add);
            }

            detailDTO.setAdditionalServices(
                    productAdditionalServicesService.queryProductAdditionalServicesByProductId(product.getProductId()));
            detailDTO.setParameters(productParameterService.queryProductParameterList(product.getProductId()));
            detailDTO.setLabels(productLabelService.getProductLabelList(product.getProductId()));
        } catch (Exception e) {
            throw e;
        }
        return detailDTO;
    }


    @Override
    public Boolean examineProductConfirm(Integer id, EnumProductAuditState auditState, String reason, String operator,Integer isOnLine) {
        Product bean = this.productDao.getOne(new QueryWrapper<Product>().eq("id", id).isNull("delete_time"));
        if (bean == null) {
            CheckDataUtils.dataError("商品不存在");
        }
        Date now = new Date();
        bean.setAuditState(auditState);
        bean.setUpdateTime(now);
        bean.setIsOnLine(isOnLine);
        if (Objects.equals(auditState, ProductStatus.AUDIT_FAIL_STATS.getCode())) {
            CheckDataUtils.judgeNull(reason);
        }
        bean.setAuditRefuseReason(reason);
        this.productAuditLogDao.save(ProductAuditLog.builder()
                .auditStatus(auditState)
                .createTime(now)
                .operator(operator)
                .feedBack(reason)
                .itemId(bean.getProductId())
                .build());
        this.productDao.updateById(bean);
        if (Objects.equals(auditState, EnumProductAuditState.PASS) && Objects.equals(bean.getType(), EnumProductType.ON_SHELF)) {
            repairNoRealName(bean.getProductId(), isOnLine);
            AsyncUtil.runAsync(
                    () -> opeCategoryProductService.changeOpeCategoryProductStatus(bean, bean.getCategoryId(), ProductStatus.STATUS.getCode(), null));
            AsyncUtil.runAsync(
                    () -> opeCustomProductService.repairCusProduct(ProductStatus.STATUS.getCode(), bean, null));
            bean.setStatus(EnumProductStatus.VALID);
        }
        this.productSnapshotsService.insertProductSnap(bean.getProductId());

        //删除缓存
        RedisUtil.del(ProductKeys.PRODUCT_DETAIL + bean.getProductId());
        return Boolean.TRUE;
    }


    private void repairNoRealName(String productId, Integer isOnLine) {
        if (null != isOnLine && isOnLine.intValue() == 3) {
            ProductSwipingCache.add(productId);
        }else {
            ProductSwipingCache.remove(productId);
        }
    }


}