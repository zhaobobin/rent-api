        
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.OpeCategoryDto;
import com.rent.common.dto.product.CategoryProductResp;
import com.rent.common.dto.product.LiteCategoryDto;
import com.rent.common.dto.product.OpeCategoryReqDto;

import java.util.List;

/**
 * 前台类目控制器Service
 * @author youruo
 * @Date 2020-06-15 11:07
 */
public interface OpeCategoryService {

        /**
         * 查询一级类目
         * @return
         */
        List<OpeCategoryDto> selectParentCategoryList();

        /**
         * 新增前台类目控制器
         *
         * @param request 条件
         * @return boolean
         */
        Integer addOpeCategory(OpeCategoryDto request);

        /**
         * 根据条件列表
         * @param request
         * @return
         */
        Page<OpeCategoryDto> queryOpeCategoryPage(OpeCategoryReqDto request);

        /**
         * 根据 ID 修改前台类目控制器
         *
         * @param request 条件
         * @return String
         */
        Boolean modifyOpeCategory(OpeCategoryDto request);

        /**
         * 删除前端类目
         *
         * @param id 条件
         * @return Integer
         */
        Boolean removeOperateCategory(Integer id);



        /**
         * 小程序lite版本查询分类
         * @param parentId
         * @param channelId
         * @return
         */
        List<LiteCategoryDto> liteCategory(Integer parentId, String channelId);


        /**
         * 查询分类下lite商品
         * @param categoryId
         * @param pageNum
         * @param pageSize
         * @return
         */
        Page<CategoryProductResp> listProduct(Integer categoryId, Integer pageNum, Integer pageSize);

        /**
         * 获取二级或三级类目ids
         * @param categoryId
         * @return
         */
        List<Integer> getCateIds(Integer categoryId,Integer status);



}