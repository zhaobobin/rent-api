package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ExamineProductQueryReqDto;
import com.rent.common.dto.product.ExamineProductResponse;
import com.rent.common.dto.product.ProductDto;
import com.rent.common.enums.product.EnumProductAuditState;

/**
 * @program: hzsx-rent-parent
 * @description:商品审核模块
 * @author: yr
 * @create: 2020-06-24 11:36
 **/

public interface ExamineProductService {

    /**
     * <p>
     * 根据条件列表
     * </p>
     *
     * @param model 实体对象
     * @return Product
     */
    Page<ProductDto> selectExaminePoroductList(ExamineProductQueryReqDto model);


    /**
     *
     * @param productId
     * @return
     */
    ExamineProductResponse selectExamineProductDetailV1(String productId);


    /**
     * 审核商品
     *
     * @param id
     * @param auditState
     * @param reason
     * @param operator
     * @param isOnLine
     * @return
     */
    Boolean examineProductConfirm(Integer id, EnumProductAuditState auditState, String reason, String operator,Integer isOnLine);




}
