
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.dto.marketing.OpeCategoryDto;
import com.rent.common.dto.product.CategoryProductResp;
import com.rent.model.product.OpeCategory;
import com.rent.model.product.OpeCategoryProduct;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 前台类目控制器Service
 *
 * @author youruo
 * @Date 2020-06-15 11:07
 */
public class OpeCategoryConverter {

    public static OpeCategory dto2Model(OpeCategoryDto dto) {
        if (dto == null) {
            return null;
        }
        OpeCategory model = new OpeCategory();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setName(dto.getName());
        model.setIcon(dto.getIcon());
        model.setParentId(dto.getParentId());
        model.setSortRule(dto.getSortRule());
        model.setBannerIcon(dto.getBannerIcon());
        model.setStatus(dto.getStatus());
        model.setZfbCode(dto.getZfbCode());
        model.setType(dto.getType());
        model.setAntChainCode(dto.getAntChainCode());
        return model;
    }


    public static List<CategoryProductResp> opeProductModelToResp(List<OpeCategoryProduct> opeProductList, Map<String,List<String>> labelsMap){
        if(CollectionUtil.isEmpty(opeProductList)){
            return Collections.emptyList();
        }
        List<CategoryProductResp> dtoList = new ArrayList<>(opeProductList.size());
        for (OpeCategoryProduct opeProduct : opeProductList) {
            CategoryProductResp resp = new CategoryProductResp();
            resp.setName(opeProduct.getName());
            resp.setImage(opeProduct.getImage());
            resp.setCategoryId(opeProduct.getOperateCategoryId());
            resp.setPrice(opeProduct.getPrice());
            resp.setOldNewDegree(opeProduct.getOldNewDegree());
            resp.setItemId(opeProduct.getItemId());
            resp.setSalesVolume(ProductSalesCache.getProductSales(opeProduct.getItemId()));
            resp.setStatus(opeProduct.getStatus());
            List<String> labels = labelsMap.get(opeProduct.getItemId());
            if(CollectionUtils.isNotEmpty(labels)){
                resp.setLabels(labels);
            }else{
                resp.setLabels(Collections.EMPTY_LIST);
            }
            dtoList.add(resp);
        }
        return dtoList;
    }

    public static List<OpeCategoryDto> modelList2DtoList(List<OpeCategory> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OpeCategoryConverter::model2Dto));
    }

    public static OpeCategoryDto model2Dto(OpeCategory model) {
        if (model == null) {
            return null;
        }
        OpeCategoryDto dto = new OpeCategoryDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setIcon(model.getIcon());
        dto.setParentId(model.getParentId());
        dto.setSortRule(model.getSortRule());
        dto.setBannerIcon(model.getBannerIcon());
        dto.setStatus(model.getStatus());
        dto.setZfbCode(model.getZfbCode());
        dto.setType(model.getType());
        dto.setAntChainCode(model.getAntChainCode());
        return dto;
    }

}