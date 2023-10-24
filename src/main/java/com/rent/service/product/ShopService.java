
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.BusShopDetailDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.product.ShopReqDto;
import com.rent.common.dto.product.ToExamineListRequestModel;

import java.util.List;
import java.util.Map;

/**
 * 店铺表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
public interface ShopService {

    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return Shop
     */
    ShopDto queryByShopId(String shopId);

    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return Shop
     */
    ShopDto queryShopDetail(ShopReqDto request);

    /**
     * 商家店铺详情
     *
     * @param shopId
     * @return
     */
    BusShopDetailDto selectBusShopInfo(String shopId);

    /**
     * 新增或修改商家和企业资质信息
     *
     * @return
     */
    Boolean updateShopAndEnterpriseInfo(BusShopDetailDto busShopDetailDto);

    /**
     * 店铺审核列表
     * @param toExamineListRequestModel 实体对象
     * @return Shop
     */
    Page<ShopDto> toShopExamineList(ToExamineListRequestModel toExamineListRequestModel);

    /**
     * 店铺资质确认
     * @param id,status,reason 参数
     * @return ShopEnterpriseInfos
     */
    String toShopExamineConfirm(Integer id, Integer status, String reason, Integer isLocked);


    /**
     * 冻结(解冻)店铺信息
     * @param shopId,status 参数
     */
    void lockedShop(String shopId, Integer status);

    /**
     * 验证店铺是否正常
     * @param shopId
     */
    void checkShop(String shopId);

    /**
     * <p>
     * 查询商家电话
     * </p>
     *
     * @param shopId
     * @return Shop
     */
    String selectShopServiceTelInfo(String shopId);

    /**
     * 获取店铺信息
     *
     * @param shopIdList
     * @return
     */
    Map<String, ShopDto> selectShopInfoByShopId(List<String> shopIdList);


    List<String> getShopIdListLikeName(String name);


    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param shopName
     * @return Shop
     */
    List<ShopDto> listAllShop(String shopName);
}