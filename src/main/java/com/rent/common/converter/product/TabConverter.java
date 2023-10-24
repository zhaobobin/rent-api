        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.dto.backstage.request.AddTabReq;
import com.rent.common.dto.backstage.request.UpdateTabReq;
import com.rent.common.dto.backstage.resp.BackstageTabProductResp;
import com.rent.common.dto.backstage.resp.TabDetailResp;
import com.rent.common.dto.backstage.resp.TabListResp;
import com.rent.common.dto.product.AvailableTabProductResp;
import com.rent.common.dto.product.TabProductResp;
import com.rent.model.product.Product;
import com.rent.model.product.Tab;
import com.rent.model.product.TabProduct;

import java.util.*;

/**
 * @author zhaowenchao
 */
public class TabConverter {

    public static Tab addTabReqToModel(AddTabReq dto) {
        if(dto==null){
            return null;
        }
        Tab tab = new Tab();
        tab.setChannelId(dto.getChannelId());
        tab.setIndexSort(dto.getIndexSort());
        tab.setName(dto.getName());
        tab.setJumpUrl(dto.getJumpUrl());
        tab.setCreateTime(new Date());
        tab.setUpdateTime(new Date());
        return tab;
    }

    public static Tab updateTabReqToModel(UpdateTabReq dto) {
        if(dto==null){
            return null;
        }
        Tab tab = new Tab();
        tab.setChannelId(dto.getChannelId());
        tab.setId(dto.getId());
        tab.setIndexSort(dto.getIndexSort());
        tab.setName(dto.getName());
        tab.setJumpUrl(dto.getJumpUrl());
        tab.setUpdateTime(new Date());
        return tab;
    }


    public static TabDetailResp modelToDetailResp(Tab model){
        if(model==null){
            return null;
        }
        TabDetailResp dto = new TabDetailResp();
        dto.setId(model.getId());
        dto.setIndexSort(model.getIndexSort());
        dto.setName(model.getName());
        dto.setJumpUrl(model.getJumpUrl());
        return dto;
    }

    public static List<TabListResp> modelsToListResp(List<Tab> models){
        if(CollectionUtil.isEmpty(models)){
           return Collections.emptyList();
        }
        List<TabListResp> dtoList = new ArrayList<>(models.size());
        for (Tab model : models) {
            TabListResp dto = new TabListResp();
            dto.setId(model.getId());
            dto.setName(model.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }


    public static List<AvailableTabProductResp> modelsToAvailableTabProductResp(List<Product> products, Map<String,String> shopIdNameMap){
        if(CollectionUtil.isEmpty(products)){
            return Collections.emptyList();
        }
        List<AvailableTabProductResp> dtoList = new ArrayList<>(products.size());
        for (Product product : products) {
            AvailableTabProductResp resp = new AvailableTabProductResp();
            resp.setProductId(product.getProductId());
            resp.setProductName(product.getName());
            resp.setShopName(shopIdNameMap.get(product.getShopId()));
            dtoList.add(resp);
        }
        return dtoList;
    }


    public static List<TabProductResp> tabProductModelToResp(List<TabProduct> tabProductList, Map<String,List<String>> labelsMap){
        if(CollectionUtil.isEmpty(tabProductList)){
            return Collections.emptyList();
        }
        List<TabProductResp> dtoList = new ArrayList<>(tabProductList.size());
        for (TabProduct tabProduct : tabProductList) {
            TabProductResp resp = new TabProductResp();
            resp.setId(tabProduct.getId());
            resp.setIndexSort(tabProduct.getIndexSort());
            resp.setName(tabProduct.getName());
            resp.setTabId(tabProduct.getTabId());
            resp.setImage(tabProduct.getImage());
            resp.setPrice(tabProduct.getPrice());
            resp.setOldNewDegree(tabProduct.getOldNewDegree());
            resp.setItemId(tabProduct.getItemId());
            resp.setLinkUrl(tabProduct.getLinkUrl());
            resp.setShopName(tabProduct.getShopName());
            resp.setSalesVolume(ProductSalesCache.getProductSales(tabProduct.getItemId()));
            resp.setStatus(tabProduct.getStatus());
            List<String> labels = labelsMap.get(tabProduct.getItemId());
            if(CollectionUtils.isNotEmpty(labels)){
                resp.setLabels(labels);
            }else{
                resp.setLabels(Collections.EMPTY_LIST);
            }
            dtoList.add(resp);
        }
        return dtoList;
    }

    public static List<BackstageTabProductResp> tabProductModelToOpeResp(List<TabProduct> tabProductList){
        if(CollectionUtil.isEmpty(tabProductList)){
            return Collections.emptyList();
        }
        List<BackstageTabProductResp> dtoList = new ArrayList<>(tabProductList.size());
        for (TabProduct tabProduct : tabProductList) {
            BackstageTabProductResp resp = new BackstageTabProductResp();
            resp.setId(tabProduct.getId());
            resp.setIndexSort(tabProduct.getIndexSort());
            resp.setName(tabProduct.getName());
            resp.setItemId(tabProduct.getItemId());
            resp.setShopName(tabProduct.getShopName());
            dtoList.add(resp);
        }
        return dtoList;
    }



}