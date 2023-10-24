        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductEvaluation;


/**
 * ProductEvaluationDao
 *
 * @author zhao
 * @Date 2020-07-22 20:51
 */
public interface ProductEvaluationDao extends IBaseDao<ProductEvaluation> {

//    /**
//     * 分页获取主评价
//     * @param request
//     * @return
//     */
//    Page<ProductEvaluationDto> pageMain(ProductEvaluationReqDto request);
//
//    Page<ProductEvaluationDto> pageMainV1(Integer pageNumber, Integer pageSize, String productId);
//
//
//    /**
//     * 获取到一个评论的追加评论
//     * @param id
//     * @return
//     */
//    List<ProductEvaluationDto> getAppend(Long id);
//
//
//    /**
//     * 将评价的【是否有追加评价】字段更新为T
//     * @param parentId
//     */
//    void updateParentAppend(Long parentId);
//
//    /**
//     * 判断订单是否已经评价
//     * @param orderId
//     * @return
//     */
//    ProductEvaluation getByOrderId(String orderId);
//
    /**
     * 查询订单是否已经评论过了
     * @param orderId
     * @return
     */
    Boolean checkOrderEvaluation(String orderId);
//
//    List<Map<String, Object>>  queryProductEvaluationImportPage(ProductEvaluationReqDto request);
//    List<Map<String, Object>>  exportProductEvaluation(ProductEvaluationReqDto request);
//
//    Integer countProductEvaluationImportPage(ProductEvaluationReqDto request);
//
//    void batchEffectProductEvaluation(List<Long> ids, Integer status);

}
