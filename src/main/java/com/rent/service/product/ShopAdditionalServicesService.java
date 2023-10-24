
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 店铺增值服务表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
public interface ShopAdditionalServicesService {

    /**
     * 新增店铺增值服务表
     *
     * @param request 条件
     * @return boolean
     */
    Integer addShopAdditionalServices(ShopAdditionalServicesDto request);

    /**
     * @param request
     * @return
     */
    Boolean saveOrUpdateShopAdditionServices(ShopAdditionalServicesDto request);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean deleteShopAdditionService(Integer id);

    /**
     * 根据 ID 修改店铺增值服务表
     *
     * @param request 条件
     * @return String
     */
    Boolean modifyShopAdditionalServices(ShopAdditionalServicesDto request);

    /**
     * <p>
     * 根据条件列表
     * </p>
     *
     * @param name
     * @return ShopAdditionalServices
     */
    Page<ShopAdditionalServicesDto> selectShopAdditionalServicesList(@RequestParam("name") String name, @RequestParam("shopId") String shopId, @RequestParam("approvalStatus") Integer approvalStatus,  @RequestParam("pageSize") Integer pageSize);

}