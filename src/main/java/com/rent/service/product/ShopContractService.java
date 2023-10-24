
package com.rent.service.product;

import com.rent.common.dto.components.dto.ShopContractDto;


/**
 * 店铺合同
 * @author zhaowenchao
 */
public interface ShopContractService {


    /**
     * 获取商家入驻协议内容
     * @param shopId
     * @return
     */
    ShopContractDto getShopContractData(String shopId);

    /**
     * 签署店铺合同
     * @param shopId
     * @return
     */
    String signShopContract(String shopId);
}