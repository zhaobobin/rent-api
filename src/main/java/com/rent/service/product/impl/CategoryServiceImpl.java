
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.product.CategoryNodeResponse;
import com.rent.common.dto.product.OpeCategoryNameAndIdDto;
import com.rent.dao.product.OpeCategoryDao;
import com.rent.dao.product.OpeCategoryProductDao;
import com.rent.model.product.OpeCategory;
import com.rent.model.product.OpeCategoryProduct;
import com.rent.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 后台商品类目表Service
 *
 * @author youruo
 * @Date 2020-06-15 10:57
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final OpeCategoryDao opeCategoryDao;
    private final OpeCategoryProductDao opeCategoryProductDao;

    @Override
    public List<CategoryNodeResponse> findCategories() {
        List<OpeCategory> parentCategoriesList = this.opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                .eq("parent_id", 0)
                .isNull("delete_time")
        );
        List<CategoryNodeResponse> nodes = new ArrayList<>();
        for (OpeCategory parentCategory : parentCategoriesList) {
            CategoryNodeResponse parentNode = new CategoryNodeResponse();
            parentNode.setValue(parentCategory.getId().longValue());
            parentNode.setLabel(parentCategory.getName());
            parentNode.setParentId(parentCategory.getParentId().longValue());
            nodes.add(parentNode);
            List<OpeCategory> childCategoryList = this.opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                    .eq("parent_id", parentCategory.getId())
                    .isNull("delete_time")
            );
            if (CollectionUtils.isNotEmpty(childCategoryList)) {
                List<CategoryNodeResponse> childNodes = new ArrayList<>(childCategoryList.size());
                childCategoryList.forEach(childCategory -> {
                    CategoryNodeResponse childNode = new CategoryNodeResponse();
                    childNode.setValue(childCategory.getId().longValue());
                    childNode.setLabel(childCategory.getName());
                    childNode.setParentId(childCategory.getParentId().longValue());
                    List<OpeCategory> threeChildCategoryList = this.opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                            .eq("parent_id", childCategory.getId())
                            .isNull("delete_time")
                    );
                    if(CollectionUtils.isNotEmpty(threeChildCategoryList)){
                        List<CategoryNodeResponse> threeChildNodes = new ArrayList<>(childCategoryList.size());
                        threeChildCategoryList.forEach(threeChildCategory->{
                            CategoryNodeResponse threeChildNode = new CategoryNodeResponse();
                            threeChildNode.setValue(threeChildCategory.getId().longValue());
                            threeChildNode.setLabel(threeChildCategory.getName());
                            threeChildNode.setParentId(threeChildCategory.getParentId().longValue());
                            threeChildNodes.add(threeChildNode);
                        });
                        childNode.setChildren(threeChildNodes);
                    }
                    childNodes.add(childNode);
                });
                parentNode.setChildren(childNodes);
            }
        }
        return nodes;
    }



    @Override
    public OpeCategoryNameAndIdDto categoryStrV1(Integer id, String productId) {
        OpeCategoryNameAndIdDto bean = new OpeCategoryNameAndIdDto();

        StringBuffer sb = new StringBuffer();
        List<Integer> sbId = new ArrayList<>();
        OpeCategoryProduct opeCategoryProduct = this.opeCategoryProductDao.getOne(new QueryWrapper<OpeCategoryProduct>()
                .eq("item_id", productId)
                .isNull("delete_time")
                .orderByDesc("id")
                .last("limit 0,1")
        );
        if (null != opeCategoryProduct) {
            OpeCategory category = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("id", opeCategoryProduct.getOperateCategoryId())
                    .isNull("delete_time")
            );

            if (category != null) {
                sb.append(category.getName()).append("`");
                sbId.add(category.getId());
                OpeCategory category1 = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                        .eq("id", category.getParentId())
                        .isNull("delete_time")
                );

                if (category1 != null) {
                    sb.append(category1.getName()).append("`");
                    sbId.add(category1.getId());
                    OpeCategory category2 = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                            .eq("id", category1.getParentId())
                            .isNull("delete_time")
                    );
                    if (category2 != null) {
                        sb.append(category2.getName()).append("`");
                        sbId.add(category2.getId());
                    }
                }


            }
            String s = sb.toString();
            String[] split = s.trim().split("`");
            List<String> list1 = new ArrayList<>();
            for (String s1 : split) {
                list1.add(s1);
            }

            Collections.swap(list1, 0, list1.size() - 1);
            Collections.reverse(sbId);

            bean.setName(list1);
            bean.setCategoryId(sbId);
        }
        return bean;
    }


}