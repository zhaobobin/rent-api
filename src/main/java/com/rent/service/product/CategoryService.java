
package com.rent.service.product;

import com.rent.common.dto.product.CategoryNodeResponse;
import com.rent.common.dto.product.OpeCategoryNameAndIdDto;

import java.util.List;

/**
 * 后台商品类目表Service
 *
 * @author youruo
 * @Date 2020-06-15 10:56
 */
public interface CategoryService {



    /**
     * <p>
     * 后台查询类目
     * </p>
     *
     * @return Category
     */
    List<CategoryNodeResponse> findCategories();



    OpeCategoryNameAndIdDto categoryStrV1(Integer id, String productId);

}