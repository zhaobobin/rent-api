package com.rent.service.product;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.api.ProductSearchDto;
import com.rent.common.dto.backstage.OnShelfProductDto;
import com.rent.common.dto.backstage.OnShelfProductReqDto;
import com.rent.common.dto.backstage.UpdateExamineProductDto;
import com.rent.common.dto.backstage.UpdateProductHiddenReqDto;
import com.rent.common.dto.product.*;
import com.rent.model.product.Product;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2021-11-25 下午 1:36:46
 * @since 1.0
 */
public interface ProductService {

    Product getByProductId(String productId);

    /**
     * 商家新增商品
     *
     * @param model
     * @return
     */
    Boolean busInsertProduct(ShopProductAddReqDto model);

    /**
     * 查询类目商品信息
     *
     * @param productId
     * @return
     */
    ProductShopCateReqDto selectProductCateByProductId(String productId);

    /**
     * 获取热租榜商品
     * @param productIds
     * @return
     */
    List<ListProductDto> getHotRentProduct(List<String> productIds);

    /**
     * <p>
     * 商家
     * </p>
     *
     * @param model 实体对象
     * @return Product
     */
    Page<ProductDto> selectBusinessProduct(ShopProductSerachReqDto model);

    /**
     * 获取确认订单页面商品信息
     * @param skuId
     * @return
     */
    ConfirmOrderProductDto getConfirmData(Long skuId,Integer days);

    /**
     * 商家后台编辑商品时查询
     *
     * @param id
     * @return
     */
    ShopProductAddReqDto selectProductEdit(Integer id);

    /**
     * 商家修改商品
     *
     * @param model
     * @return
     */
    Boolean updateBusProduct(ShopProductAddReqDto model);

    /**
     * 小程序商品查询
     * @return
     */
    Page<ShopProductAddReqDto> searchProduct(ProductSearchDto searchDto);

    /**
     * 简版小程序获取商品详情
     * @param productId
     * @return
     */
    ShopProductAddReqDto getProductDetailLite(String productId);

    /**
     * 商家推荐商品-简版小程序
     * @param productId
     * @return
     */
    List<ProductDetailRecommendResp> recommendLite(String productId);

    /**
     * 根据商品ID数组获取商品列表
     * @param productIds
     * @return
     */
    List<ListProductDto> getProductInByIds(List<String> productIds);

    /**
     * 根据商品ID数组获取商品列表 带标签
     * @param productIds
     * @return
     */
    List<ListProductDto> getProductInByIdsV1(List<String> productIds);
    /**
     * 简版小程序获取优惠券使用范围内的商品
     * @param request
     * @return
     */
    Page<ListProductDto> getLiteProductInRange(CouponRangeProductReqDto request);

    /**
     * 查询商品基本信息,获取商品审核通过&上架&有效
     * @param productId
     * @return
     */
    ShopProductAddReqDto selectProductDetailByProductId(String productId);

    /**
     * 查询商品名称
     * @param productIds
     * @return
     */
    Map<String, String> getProductName(List<String> productIds);

    /**
     * 根据产品名称查询产品列表
     * @param name
     * @return
     */
    List<ProductDto> selectProductByName(String name);

    /**
     * 查询归还物流支付方式
     * @param productId
     * @return
     */
    String getReturnFreightType(String productId);

    /**
     * 更新上下架商品
     *
     * @param id
     * @return
     */
    Boolean busUpdateProduct(@RequestParam("id") Integer id, @RequestParam("type") Integer type, @RequestParam("status") Integer status);

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    Boolean busUpdateProductByRecycleDel(@RequestParam("id") Integer id);

    JSONObject selectShopProductListAndShopV1(String shopId, Integer pageNumber, Integer pageSize);

    /**
     * 查询审核商品
     *
     * @param productId
     * @return
     */
    Map<String, Object> selectExamineProductEdit(String productId);

    /**
     * 修改审核商品
     *
     * @param model
     * @return
     */
    Boolean updateExamineProduct(@RequestBody UpdateExamineProductDto model);






    /**
     * 定时任务初始化商品排序分数值
     * @param request
     * @return
     */
    Boolean initProductScore(List<OnShelfProductReqDto> request);

    /**
     * 更新展示与隐藏状态
     * @param productIdList
     * @return
     */
    Boolean updateProductHidden(UpdateProductHiddenReqDto productIdList);

    /**
     * 查询已经上架商品 以及排序顺序
     * @param request
     * @return
     */
    Page<OnShelfProductDto> queryOnShelfProduct(OnShelfProductReqDto request);

    /**
     * 给商品排序分加值
     * @param request
     * @return
     */
    Boolean addScore(OnShelfProductReqDto request);

    /**
     *
     * @param keyWord
     * @param pageNumber
     * @param pageSize
     * @param shopId
     * @return
     */
    Page<AbleProductDto> ableProductV1(String keyWord, Integer pageNumber, Integer pageSize,String shopId);
}
