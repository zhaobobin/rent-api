
package com.rent.service.product;

import com.rent.common.dto.product.ProductGiveBackAddressesDto;
import com.rent.common.dto.product.ShopGiveBackAddressesDto;

import java.util.List;

/**
 * 商品归还地址Service
 *
 * @author youruo
 * @Date 2020-06-16 15:12
 */
public interface ProductGiveBackAddressesService {

    /**
     * 获取商品归还地址
     * @param productId
     * @return
     */
    List<Integer> getAddIds(String productId);

    /**
     * 商品批量增加退回信息
     *
     * @param productId
     * @param addIds
     */
    void insertGiveBackAddress(String productId, List<Integer> addIds);
//
    /**
     * 商品批量修改退回信息
     * @param productId
     * @param addIds
     */
    void updateGiveBackAddress(String productId, List<Integer> addIds);


    /**
     * 新增商品归还地址
     *
     * @param request 条件
     * @return boolean
     */
    Integer addProductGiveBackAddresses(ProductGiveBackAddressesDto request);

    /**
     * 查询商品归还列表
     * @param itemId
     * @return
     */
    List<ShopGiveBackAddressesDto> queryProductGiveBackList(String itemId);

    List<ShopGiveBackAddressesDto> getGiveBack(String productId);


}