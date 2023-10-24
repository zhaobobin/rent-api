        
package com.rent.service.product.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.converter.product.TabConverter;
import com.rent.common.dto.backstage.request.*;
import com.rent.common.dto.backstage.resp.BackstageTabProductResp;
import com.rent.common.dto.backstage.resp.TabDetailResp;
import com.rent.common.dto.backstage.resp.TabListResp;
import com.rent.common.dto.product.*;
import com.rent.common.enums.common.EnumProductError;
import com.rent.common.enums.product.ProductStatus;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.product.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.*;
import com.rent.service.product.TabService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TabServiceImpl implements TabService {

    private final TabDao tabDao;
    private final TabProductDao tabProductDao;
    private final ProductLabelDao productLabelDao;
    private final ShopDao shopDao;
    private final ProductDao productDao;
    private final ProductSnapshotsDao productSnapshotsDao;
    private final UserOrdersDao userOrdersDao;

    @Override
    public Boolean add(AddTabReq request) {
        tabDao.save(TabConverter.addTabReqToModel(request));
        return Boolean.TRUE;
    }

    @Override
    public Boolean update(UpdateTabReq request) {
        tabDao.updateById(TabConverter.updateTabReqToModel(request));
        return Boolean.TRUE;
    }

    @Override
    public Boolean delete(Long id) {
        Tab tab = new Tab();
        tab.setId(id);
        tab.setDeleteTime(new Date());
        tabDao.updateById(tab);
        return Boolean.TRUE;
    }

    @Override
    public List<TabListResp> list(String channelId) {
        List<Tab> list = tabDao.list(new QueryWrapper<Tab>()
                .select("id","name")
                .eq("channel_id",channelId)
                .isNull("delete_time")
                .orderByAsc("index_sort"));
        return TabConverter.modelsToListResp(list);
    }

    @Override
    public TabDetailResp getById(Long id) {
        Tab tab = tabDao.getById(id);
        if(tab.getDeleteTime()!=null){
            return null;
        }
        return TabConverter.modelToDetailResp(tabDao.getById(id));
    }

    @Override
    public Page<AvailableTabProductResp> getAvailableProduct(QueryAvailableTabProductReq request) {
        List<Shop> shopList = shopDao.list(new QueryWrapper<Shop>()
                .select("name","shop_id")
                .isNull("delete_time")
                .isNotNull("approval_time")
                .eq("is_locked",0)
                .eq("is_disabled",0)
                .like(StringUtils.isNotEmpty(request.getShopName()),"shopName",request.getShopName()));
        List<String> shopIdList = shopList.stream().map(Shop::getShopId).collect(Collectors.toList());
        Map<String,String> shopIdNameMap = shopList.stream().collect(Collectors.toMap(Shop::getShopId,Shop::getName));
        List<String> exitsProductIds = tabProductDao.getProductIds(request.getTabId());
        Page<Product> page = productDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
            new QueryWrapper<Product>()
                    .select("product_id","status","name","shop_id")
                    .in("shop_id",shopIdList)
                    .eq("type",1)
                    .eq("audit_state",2)
                    .eq("status",1)
                    .like(StringUtils.isNotEmpty(request.getProductName()),"name",request.getProductName())
                    .eq(StringUtils.isNotEmpty(request.getProductId()),"product_id",request.getProductId())
                    .notIn(CollectionUtils.isNotEmpty(exitsProductIds),"product_id",exitsProductIds)
                    .isNull("delete_time"));

        return new Page<AvailableTabProductResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                TabConverter.modelsToAvailableTabProductResp(page.getRecords(),shopIdNameMap));
    }

    @Override
    public Boolean addProduct(AddTabProductReq request) {
        Integer nextSort = tabProductDao.getNextSort(request.getTabId());
        List<TabProduct> tabProductList = new ArrayList<>(request.getProductIds().size());
        for (String productId : request.getProductIds()) {
            if(!tabProductDao.checkTabProductExits(request.getTabId(),productId)){
                tabProductList.add(buildOpeCustomProduct(productId,request.getTabId(),nextSort));
                nextSort++;
            }
        }
        tabProductDao.saveBatch(tabProductList);
        return Boolean.TRUE;
    }


    @Override
    public Page<TabProductResp> listProductForApi(Long tabId, Integer pageNum, Integer pageSize) {
        Page<TabProduct> page = tabProductDao.page(new Page<>(pageNum, pageSize),new QueryWrapper<TabProduct>().eq("tab_id",tabId).isNull("delete_time").orderByAsc("index_sort"));
        if(CollectionUtils.isEmpty(page.getRecords())){
            return new Page<TabProductResp>(pageNum, pageSize, 0).setRecords(Collections.EMPTY_LIST);
        }
        List<String> productIdList  = page.getRecords().stream().map(TabProduct::getItemId).collect(Collectors.toList());
        Map<String,List<String>> labelsMap = productLabelDao.getProductLabelList(productIdList);
        return new Page<TabProductResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                TabConverter.tabProductModelToResp(page.getRecords(),labelsMap));
    }

    @Override
    public Page<BackstageTabProductResp> listProduct(Long tabId, Integer pageNum, Integer pageSize) {
        Page<TabProduct> page = tabProductDao.page(new Page<>(pageNum, pageSize),new QueryWrapper<TabProduct>().eq("tab_id",tabId).isNull("delete_time").orderByAsc("index_sort"));
        if(CollectionUtils.isEmpty(page.getRecords())){
            return new Page<BackstageTabProductResp>(pageNum, pageSize, 0).setRecords(Collections.EMPTY_LIST);
        }
        return new Page<BackstageTabProductResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(TabConverter.tabProductModelToOpeResp(page.getRecords()));
    }

    @Override
    public Boolean updateProductSort(UpdateTabProductSortReq request) {
        TabProduct tabProduct = new TabProduct();
        tabProduct.setIndexSort(request.getIndexSort());
        tabProduct.setId(request.getId());
        tabProductDao.updateById(tabProduct);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteProduct(Long id) {
        TabProduct tabProduct = new TabProduct();
        tabProduct.setDeleteTime(new Date());
        tabProduct.setId(id);
        tabProductDao.updateById(tabProduct);
        return Boolean.TRUE;
    }


    private TabProduct buildOpeCustomProduct(String itemId, Long tabId,Integer sort) {
        Date date = new Date();
        ProductSnapshots productSnapshots = productSnapshotsDao.getOne(new QueryWrapper<ProductSnapshots>().eq("item_id",itemId).orderByDesc("version").last("limit 0,1"));
        if (null == productSnapshots) {
            throw new HzsxBizException(EnumProductError.PRODUCT_NOT_EXISTS.getCode(),EnumProductError.PRODUCT_NOT_EXISTS.getMsg());
        }
        ShopProductSnapResponse productSnapshot = JSONObject.parseObject((String) productSnapshots.getData(),ShopProductSnapResponse.class);
        List<ProductImageDto> images = productSnapshot.getProductImage();
        TabProduct product = new TabProduct();
        if (CollectionUtils.isNotEmpty(images)) {
            String src = images.get(0).getSrc();
            product.setImage(src);
        }
        product.setCreateTime(date);
        List<ShopProductSnapSkusResponse> shopProductSnapSkus = productSnapshot.getShopProductSnapSkus();
        if (CollectionUtils.isNotEmpty(shopProductSnapSkus)) {
            ProductCyclePricesDto prices = getLowestPrice(shopProductSnapSkus);
            product.setPrice(prices.getPrice().toPlainString());
            ProductSkusDto productSkusDto = getLowestPriceSku(shopProductSnapSkus, prices.getSkuId().intValue());
            product.setOldNewDegree(productSkusDto.getOldNewDegree().toString());
        }
        ProductDto productDto = productSnapshot.getProduct();
        if (null != productDto) {
            product.setName(productDto.getName());
        }
        product.setCreateTime(date);
        product.setIndexSort(sort);
        product.setItemId(itemId);
        product.setTabId(tabId);
        product.setShopName(productSnapshot.getShop().getName());
        product.setStatus(ProductStatus.STATUS.getCode());
        product.setUpdateTime(date);
        packProductSales(product);
        return product;
    }

    private ProductSkusDto getLowestPriceSku(List<ShopProductSnapSkusResponse> snapList, Integer skuId) {
        ProductSkusDto productSkusDto = new ProductSkusDto();
        if (CollectionUtils.isNotEmpty(snapList) && null != skuId) {
            snapList = snapList.stream().filter(person -> person.getProductSkus().getId().equals(skuId)).collect(Collectors.toList());
            productSkusDto = snapList.get(0).getProductSkus();
        }
        return productSkusDto;
    }

    private ProductCyclePricesDto getLowestPrice(List<ShopProductSnapSkusResponse> snapList) {
        ProductCyclePricesDto productCyclePricesDto = new ProductCyclePricesDto();
        if (CollectionUtils.isNotEmpty(snapList)) {
            List<ProductCyclePricesDto> cyclePrices = new ArrayList<>();
            snapList.forEach(item -> {
                cyclePrices.addAll(item.getCyclePrices());
            });
            Optional<ProductCyclePricesDto> min = cyclePrices.stream().min(Comparator.comparing(ProductCyclePricesDto::getPrice));
            productCyclePricesDto = min.get();
        }
        return productCyclePricesDto;
    }

    private void packProductSales(TabProduct product) {
        Integer sales =  ProductSalesCache.getProductSales(product.getItemId());
        if(sales==null){
            sales =  userOrdersDao.getProductSales(product.getItemId());
        }
        product.setSalesVolume(sales);
    }
}