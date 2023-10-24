    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductEvaluationDao;
import com.rent.mapper.product.ProductEvaluationMapper;
import com.rent.model.product.ProductEvaluation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductEvaluationDao
 *
 * @author zhao
 * @Date 2020-07-22 20:51
 */
@Repository
public class ProductEvaluationDaoImpl extends AbstractBaseDaoImpl<ProductEvaluation, ProductEvaluationMapper> implements ProductEvaluationDao {


//    @Override
//    public Page<ProductEvaluationDto> pageMain(ProductEvaluationReqDto request) {
//        String channel =  AppParamUtil.getChannelId();
//        Page<ProductEvaluation> page = page(
//                new Page<>(request.getPageNumber(), request.getPageSize()),
//                new QueryWrapper<>(ProductEvaluationConverter.reqDto2Model(request))
//                        .like(StringUtils.isNotEmpty(channel),"channel",channel)
//                        .eq("status",0)
//                        .isNull("parent_id")
//                        .isNull("delete_time").orderByDesc("is_chosen"));
//
//        List<ProductEvaluationDto> evaluationDtoList = ProductEvaluationConverter.modelList2DtoList(page.getRecords());
//        return new Page<ProductEvaluationDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(evaluationDtoList);
//    }
//
//    @Override
//    public Page<ProductEvaluationDto> pageMainV1(Integer pageNumber, Integer pageSize, String productId) {
//        String channel =  AppParamUtil.getChannelId();
//        Page<ProductEvaluation> page = page(
//                new Page<>(pageNumber, pageSize),
//                new QueryWrapper<ProductEvaluation>()
//                        .eq(StringUtils.isNotEmpty(productId),"product_id",productId)
//                        .like(StringUtils.isNotEmpty(channel),"channel",channel)
//                        .eq("status",0)
//                        .isNull("parent_id")
//                        .isNull("delete_time").orderByDesc("is_chosen"));
//
//        List<ProductEvaluationDto> evaluationDtoList = ProductEvaluationConverter.modelList2DtoList(page.getRecords());
//        return new Page<ProductEvaluationDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(evaluationDtoList);
//    }
//
//    @Override
//    public List<ProductEvaluationDto> getAppend(Long id) {
//        List<ProductEvaluation> appendList = list(new QueryWrapper<ProductEvaluation>()
//                .eq("status",0)
//                .eq("parent_id",id).isNull("delete_time"));
//        return  ProductEvaluationConverter.modelList2DtoList(appendList);
//    }
//
//    @Override
//    public void updateParentAppend(Long id) {
//        ProductEvaluation evaluation = new ProductEvaluation();
//        evaluation.setId(id);
//        evaluation.setContainsAppend("T");
//        updateById(evaluation);
//    }
//
//    @Override
//    public ProductEvaluation getByOrderId(String orderId) {
//        return getOne(new QueryWrapper<ProductEvaluation>().eq("order_id",orderId).isNull("parent_id"));
//    }
//
    @Override
    public Boolean checkOrderEvaluation(String orderId) {
        List<ProductEvaluation> list = list(new QueryWrapper<ProductEvaluation>().select("id").eq("order_id",orderId));
        return CollectionUtils.isNotEmpty(list);
    }
//
//    @Override
//    public List<Map<String, Object>> queryProductEvaluationImportPage(ProductEvaluationReqDto request) {
//        return baseMapper.queryProductEvaluationImportPage(request);
//    }
//
//    @Override
//    public List<Map<String, Object>> exportProductEvaluation(ProductEvaluationReqDto request) {
//        return baseMapper.exportProductEvaluation(request);
//    }
//
//    @Override
//    public Integer countProductEvaluationImportPage(ProductEvaluationReqDto request) {
//        return baseMapper.countProductEvaluationImportPage(request);
//    }
//
//    @Override
//    public void batchEffectProductEvaluation(List<Long> ids, Integer status) {
//        baseMapper.batchEffectProductEvaluation(ids,status);
//    }
}
