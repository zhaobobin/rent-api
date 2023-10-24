
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.CategoryType;
import com.rent.common.dto.product.OpeCategoryNameAndIdDto;
import com.rent.common.enums.product.ProductStatus;
import com.rent.dao.product.OpeCategoryDao;
import com.rent.dao.product.OpeCategoryProductDao;
import com.rent.dao.product.ProductImageDao;
import com.rent.dao.product.ProductServiceMarksDao;
import com.rent.model.product.OpeCategory;
import com.rent.model.product.OpeCategoryProduct;
import com.rent.model.product.Product;
import com.rent.model.product.ProductImage;
import com.rent.service.product.OpeCategoryProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 前台类目商品表Service
 *
 * @author youruo
 * @Date 2020-06-15 10:28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeCategoryProductServiceImpl implements OpeCategoryProductService {

    private final OpeCategoryDao opeCategoryDao;
    private final ProductImageDao productImageDao;
    private final OpeCategoryProductDao opeCategoryProductDao;
    private final ProductServiceMarksDao productServiceMarksDao;


    @Override
    public OpeCategoryNameAndIdDto opeCategoryStr(String ProductId) {
        OpeCategoryNameAndIdDto bean = new OpeCategoryNameAndIdDto();
        List<String> list = new ArrayList<>();
        List<Integer> listId = new ArrayList<>();
        OpeCategoryProduct opeCategoryProduct = this.opeCategoryProductDao.getOne(new QueryWrapper<OpeCategoryProduct>()
                .eq("item_id", ProductId)
                .isNull("delete_time")
                .orderByDesc("id")
                .last("limit 0,1")
        );
        if (opeCategoryProduct != null) {
            OpeCategory opeCategory = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("id", opeCategoryProduct.getOperateCategoryId())
                    .isNull("delete_time")
            );
            OpeCategory opeCategoryTwo = null;
            if (opeCategory != null && CategoryType.THREE_CATEGORY.equals(opeCategory.getType())) {
                list.add(opeCategory.getName());
                listId.add(opeCategory.getId());
                opeCategoryTwo = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                        .eq("id", opeCategory.getParentId())
                        .isNull("delete_time")
                );
            } else {
                opeCategoryTwo = opeCategory;
            }
            list.add(null != opeCategoryTwo ? opeCategoryTwo.getName() : null);
            listId.add(null != opeCategoryTwo ? opeCategoryTwo.getId() : null);
            OpeCategory opeCategoryOne = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("id", null != opeCategoryTwo ? opeCategoryTwo.getParentId() : 0)
                    .isNull("delete_time")
            );
            if (opeCategoryOne != null) {
                list.add(opeCategoryOne.getName());
                listId.add(opeCategoryOne.getId());
            }
            bean.setName(list);
            bean.setCategoryId(listId);
        }
        return bean;
    }

    @Override
    public OpeCategoryNameAndIdDto opeCategoryStrV1(Integer categoryId) {
        OpeCategoryNameAndIdDto bean = new OpeCategoryNameAndIdDto();
        List<String> list = new ArrayList<>();
        List<Integer> listId = new ArrayList<>();
        OpeCategory opeCategory = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                .eq("id", categoryId)
                .isNull("delete_time")
        );
        if (null != opeCategory) {
            if (CategoryType.TWO_CATEGORY.equals(opeCategory.getType())) {
                OpeCategory opeCategoryFirst = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                        .eq("id", opeCategory.getParentId())
                        .isNull("delete_time")
                );
                if (opeCategoryFirst != null) {
                    list.add(opeCategoryFirst.getName());
                    listId.add(opeCategoryFirst.getId());
                }
            } else {
                OpeCategory opeCategoryTwo = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                        .eq("id", opeCategory.getParentId())
                        .isNull("delete_time")
                );
                if (opeCategoryTwo != null) {
                    OpeCategory opeCategoryFirst = this.opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                            .eq("id", opeCategoryTwo.getParentId())
                            .isNull("delete_time")
                    );
                    if (opeCategoryFirst != null) {
                        list.add(opeCategoryFirst.getName());
                        listId.add(opeCategoryFirst.getId());
                    }
                    list.add(opeCategoryTwo.getName());
                    listId.add(opeCategoryTwo.getId());
                }
            }
            list.add(opeCategory.getName());
            listId.add(opeCategory.getId());
        }

        bean.setName(list);
        bean.setCategoryId(listId);

        return bean;
    }

    @Override
    public void changeOpeCategoryProductStatus(Product product, Integer categoryId, Integer status, String image) {
        OpeCategoryProduct opeCategoryProduct = opeCategoryProductDao.getOne(new QueryWrapper<OpeCategoryProduct>()
                .eq("item_id", product.getProductId())
                .orderByDesc("id")
                .last("limit 1")
        );
        if (null != opeCategoryProduct) {
            OpeCategory opeCategory = opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("id", categoryId)
                    .isNull("delete_time")
            );
            if (opeCategory != null) {
                opeCategoryProduct.setOperateCategoryId(categoryId);
                opeCategoryProduct.setParentCategoryId(opeCategory.getParentId());

            }
            opeCategoryProduct.setShopId(product.getShopId());
            opeCategoryProduct.setMinRent(product.getMinRentCycle());
            opeCategoryProduct.setSalesVolume(product.getSalesVolume());
            opeCategoryProduct.setMonthlySalesVolume(product.getMonthlySalesVolume());
            opeCategoryProduct.setStatus(status);
            opeCategoryProduct.setOldNewDegree(null != product.getOldNewDegree() ? product.getOldNewDegree().toString() : null);
            opeCategoryProduct.setPrice(product.getSale());
            opeCategoryProduct.setSale(product.getSale());
            opeCategoryProduct.setImage(image);
            opeCategoryProduct.setName(product.getName());
            if (StringUtils.isEmpty(image)) {
                ProductImage productImage = productImageDao.getOne(new QueryWrapper<ProductImage>()
                                .eq("product_id", product.getProductId())
                                .isNull("delete_time")
                                .orderByAsc("id")
                                .last("limit 1")
                );
                if (productImage != null) {
                    opeCategoryProduct.setImage(productImage.getSrc());
                }
            }
            opeCategoryProduct.setDeleteTime(null);
            opeCategoryProduct.setUpdateTime(new Date());
            opeCategoryProduct.setStatus(status);
            opeCategoryProductDao.updateById(opeCategoryProduct);
        }
    }

    @Override
    public Boolean addCategoryProductCommon(Product product, Integer categoryId) {

        OpeCategoryProduct opeCategoryProduct = opeCategoryProductDao.getOne(new QueryWrapper<OpeCategoryProduct>()
                .eq("item_id", product.getProductId())
                .eq("operate_category_id", categoryId)
                .isNull("delete_time")
        );
        if (opeCategoryProduct == null) {

            //绑定原类目商品 添加到 ope_category_product
            OpeCategoryProduct categoryProduct = new OpeCategoryProduct();

            Date now = new Date();
            categoryProduct.setCreateTime(now);
            categoryProduct.setUpdateTime(now);
            categoryProduct.setOperateCategoryId(categoryId);
            categoryProduct.setFreightType(product.getFreightType());
            OpeCategory opeCategory = opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("id", categoryId)
                    .isNull("delete_time")
            );
            if (opeCategory != null) {
                categoryProduct.setParentCategoryId(opeCategory.getParentId());
            }
            categoryProduct.setItemId(product.getProductId());
            categoryProduct.setName(product.getName());
            //商品主图
            ProductImage productImage = productImageDao.getOne(new QueryWrapper<ProductImage>()
                            .eq("product_id", product.getProductId())
//                    .eq("is_main", 1)
                            .isNull("delete_time")
                            .orderByAsc("id")
            );
            if (productImage != null) {
                categoryProduct.setImage(productImage.getSrc());
            }
            categoryProduct.setShopId(product.getShopId());
            categoryProduct.setMinRent(product.getMinRentCycle());
            categoryProduct.setSalesVolume(product.getSalesVolume());
            categoryProduct.setMonthlySalesVolume(product.getMonthlySalesVolume());
            categoryProduct.setStatus(ProductStatus.NO_STATUS.getCode());
            categoryProduct.setServiceMarks(productServiceMarksDao.getServiceMark(product.getProductId()));
            categoryProduct.setOldNewDegree(null != product.getOldNewDegree() ? product.getOldNewDegree().toString() : null);
            categoryProduct.setPrice(product.getSale());
            categoryProduct.setSale(product.getSale());
            boolean insert = opeCategoryProductDao.save(categoryProduct);
            if (!insert) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Boolean deleteProduct(Integer categoryId, String itemId) {
        Date now = new Date();
        QueryWrapper<OpeCategoryProduct> wh = new QueryWrapper<>();
        wh.eq("item_id", itemId);
        wh.isNull("delete_time");
        OpeCategoryProduct ss = new OpeCategoryProduct();
        ss.setUpdateTime(now);
        ss.setDeleteTime(now);
        opeCategoryProductDao.update(ss, wh);
        return true;
    }


}