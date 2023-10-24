
package com.rent.service.product;

import com.rent.common.dto.product.ShopGiveBackAddressesDto;
import com.rent.common.dto.product.ShopGiveBackAddressesReqDto;

import java.util.List;

/**
 * 店铺归还地址表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
public interface ShopGiveBackAddressesService {

    /**
     * 新增店铺归还地址表
     *
     * @param request 条件
     * @return boolean
     */
    Boolean addShopGiveBackAddresses(ShopGiveBackAddressesDto request);

    /**
     * 根据 ID 修改店铺归还地址表
     *
     * @param request 条件
     * @return String
     */
    Boolean modifyShopGiveBackAddresses(ShopGiveBackAddressesDto request);

    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return ShopGiveBackAddresses
     */
    ShopGiveBackAddressesDto queryShopGiveBackAddressesDetail(ShopGiveBackAddressesReqDto request);

    /**
     * 根据ID查询
     * @param shopId
     * @return
     */
    List<ShopGiveBackAddressesDto> selectShopGiveBackByShopId(String shopId);

    /**
     * 删除一条记录
     * @param id
     * @param shopId
     * @return
     */
    Boolean delShopGiveBackAddressById(Integer id,String shopId);


}