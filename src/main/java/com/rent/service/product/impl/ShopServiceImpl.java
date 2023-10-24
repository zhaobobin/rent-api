
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ShopConverter;
import com.rent.common.converter.product.ShopEnterpriseCertificatesConverter;
import com.rent.common.converter.product.ShopEnterpriseInfosConverter;
import com.rent.common.dto.product.*;
import com.rent.common.enums.product.ShopStatus;
import com.rent.common.util.CheckDataUtils;
import com.rent.dao.product.ShopDao;
import com.rent.dao.product.ShopEnterpriseCertificatesDao;
import com.rent.dao.product.ShopEnterpriseInfosDao;
import com.rent.dao.product.ShopSplitBillAccountDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.Shop;
import com.rent.model.product.ShopEnterpriseInfos;
import com.rent.service.product.ShopEnterpriseInfosService;
import com.rent.service.product.ShopService;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 店铺表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopDao shopDao;
    private final ShopEnterpriseInfosDao shopEnterpriseInfosDao;
    private final ShopEnterpriseCertificatesDao shopEnterpriseCertificatesDao;
    private final ShopEnterpriseInfosService shopEnterpriseInfosService;
    private final ShopSplitBillAccountDao shopSplitBillAccountDao;

    @Override
    public ShopDto queryByShopId(String shopId) {
        ShopReqDto shopReqDto = new ShopReqDto();
        shopReqDto.setShopId(shopId);
        return this.queryShopDetail(shopReqDto);
    }

    @Override
    public ShopDto queryShopDetail(ShopReqDto request) {
        Shop shop = shopDao.getOne(new QueryWrapper<>(ShopConverter.reqDto2Model(request)));
        return ShopConverter.model2Dto(shop);
    }

    @Override
    public BusShopDetailDto selectBusShopInfo(String shopId) {
        BusShopDetailDto busShopDetailDto = new BusShopDetailDto();
        Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>()
                .eq("shop_id", shopId)
                .isNull("delete_time")
        );
        if (shop == null) {
            return busShopDetailDto;
        }
        ShopDto shopDto = ShopConverter.model2Dto(shop);
        busShopDetailDto.setShop(shopDto);
        EnterpriseInfoDto enterpriseInfoDto = shopEnterpriseInfosDao.getShopEnterpriseInfosByShopId(shopId);
        busShopDetailDto.setEnterpriseInfoDto(enterpriseInfoDto);
        return busShopDetailDto;
    }

    @Override
    public Boolean updateShopAndEnterpriseInfo(BusShopDetailDto busShopDetailDto) {
        try {
            //商店信息
            ShopDto shopDto = busShopDetailDto.getShop();
            Date now = new Date();
            //企业资质
            EnterpriseInfoDto enterpriseInfoDto = busShopDetailDto.getEnterpriseInfoDto();
            //企业资质实体
            ShopEnterpriseInfosDto shopEnterpriseInfos = enterpriseInfoDto.getShopEnterpriseInfos();
            //企业资质实体图片信息
            List<ShopEnterpriseCertificatesDto> shopEnterpriseCertificates = enterpriseInfoDto.getShopEnterpriseCertificates();
            shopEnterpriseInfos.setUpdateTime(now);
            shopEnterpriseInfos.setStatus(ShopStatus.SHOP_STATUS_SUBMIT_ENTER.getCode());
            ShopEnterpriseInfos enterpriseInfo = shopEnterpriseInfosDao.getOne(new QueryWrapper<ShopEnterpriseInfos>()
                    .eq("shop_id", shopEnterpriseInfos.getShopId())
                    .isNull("delete_time")
                    .last("limit 1")
            );
            shopEnterpriseInfos.setContactName(shopDto.getUserName());
            shopEnterpriseInfos.setContactTelephone(shopDto.getUserTel());
            shopEnterpriseInfos.setContactEmail(shopDto.getUserEmail());
            if (enterpriseInfo != null) {
                shopEnterpriseInfos.setId(enterpriseInfo.getId());
                shopEnterpriseInfos.setUpdateTime(now);
                this.shopEnterpriseInfosDao.updateById(ShopEnterpriseInfosConverter.dto2Model(shopEnterpriseInfos));
            } else {
                shopEnterpriseInfos.setCreateTime(now);
                ShopEnterpriseInfos model = ShopEnterpriseInfosConverter.dto2Model(shopEnterpriseInfos);
                shopEnterpriseInfosDao.save(model);
                shopEnterpriseInfos.setId(model.getId());
            }
            //维护企业资质图片信息
            if (CollectionUtils.isNotEmpty(shopEnterpriseCertificates)) {
                shopEnterpriseCertificates.forEach(item -> {
                    if (null != item.getId()) {
                        item.setUpdateTime(now);
                        this.shopEnterpriseCertificatesDao.updateById(ShopEnterpriseCertificatesConverter.dto2Model(item));
                    } else {
                        item.setCreateTime(now);
                        item.setUpdateTime(now);
                        item.setSeInfoId(shopEnterpriseInfos.getId());
                        this.shopEnterpriseCertificatesDao.save(ShopEnterpriseCertificatesConverter.dto2Model(item));
                    }
                });
            }


            Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>()
                    .eq("shop_id", shopDto.getShopId())
                    .isNull("delete_time")
            );
            if(StringUtil.isNotEmpty(shopEnterpriseInfos.getContactTelephone())){
                shopDto.setRecvMsgTel(shopEnterpriseInfos.getContactTelephone());
            }
            shopDto.setStatus(ShopStatus.SHOP_STATUS_AUDIT.getCode());
            if (shop != null) {
                shopDto.setUpdateTime(now);
                shopDto.setId(shop.getId());
                this.shopDao.updateById(ShopConverter.dto2Model(shopDto));
            } else {
                shopDto.setCreateTime(now);
                shopDto.setUpdateTime(now);
                shopDto.setRegistTime(String.valueOf(System.currentTimeMillis()));
                this.shopDao.save(ShopConverter.dto2Model(shopDto));
            }
        } catch (Exception e) {
            log.error("ShopEnterpriseCertificatesServiceImpl.updateShop 异常" + e.getMessage());
            CheckDataUtils.dataError(e.getMessage());
        }
        return Boolean.TRUE;
    }

    @Override
    public Page<ShopDto> toShopExamineList(ToExamineListRequestModel toExamineListRequestModel) {
        String start = null;
        String end = null;
        if (CollectionUtils.isNotEmpty(toExamineListRequestModel.getCreateDate())) {
            start = toExamineListRequestModel.getCreateDate().get(0);
            end = toExamineListRequestModel.getCreateDate().get(1);
        }
        List<String> shopNameIds = new ArrayList<>();
        if (StringUtils.isNotBlank(toExamineListRequestModel.getName()) &&
                CollectionUtils.isEmpty(shopNameIds = shopEnterpriseInfosDao.list(new QueryWrapper<ShopEnterpriseInfos>()
                        .select("shop_id")
                        .like("name", toExamineListRequestModel.getName())
                        .isNull("delete_time")
                ).stream().map(ShopEnterpriseInfos::getShopId).collect(Collectors.toList()))) {
            return new Page<ShopDto>(1, toExamineListRequestModel.getPageSize());
        }
        try {
            // 店铺审核 3正在审核 4审核不通过 5审核通过
            Page<Shop> shopPage = this.shopDao.page(new Page<>(toExamineListRequestModel.getPageNumber(), toExamineListRequestModel.getPageSize()), new QueryWrapper<Shop>()
                    .select("id", "`name`", "`status`", "shop_id", "create_time","update_time", "is_phone_examination", "is_locked")
                    .eq(StringUtils.isNotEmpty(toExamineListRequestModel.getStatus()), "`status`", toExamineListRequestModel.getStatus())
                    .eq(StringUtils.isNotEmpty(toExamineListRequestModel.getIsPhoneExamination()), "`is_phone_examination`", toExamineListRequestModel.getIsPhoneExamination())
                    .like(StringUtils.isNotEmpty(toExamineListRequestModel.getShopName()), "`name`", toExamineListRequestModel.getShopName())
                    .eq(StringUtils.isNotEmpty(toExamineListRequestModel.getShopId()), "shop_id", toExamineListRequestModel.getShopId())
                    .between(CollectionUtils.isNotEmpty(toExamineListRequestModel.getCreateDate()), "create_time", start, end)
                    .in(CollectionUtils.isNotEmpty(shopNameIds),"shop_id",shopNameIds)
                    .isNull("delete_time")
                    .in("`status`", ShopStatus.SHOP_STATUS_AUDIT.getCode(), ShopStatus.SHOP_STATUS_AUDIT_FAIL.getCode(), ShopStatus.SHOP_STATUS_AUDIT_PASS.getCode())
                    .orderByDesc("update_time")
            );
            List<ShopDto> records = ShopConverter.modelList2DtoList(shopPage.getRecords());
            if (CollectionUtils.isNotEmpty(records)) {
                for (ShopDto shop : records) {
                    ShopEnterpriseInfos shopEnterpriseInfos = this.shopEnterpriseInfosDao.getOne(new QueryWrapper<ShopEnterpriseInfos>()
                            .eq("shop_id", shop.getShopId())
                            .isNull("delete_time")
                    );
                    if (shopEnterpriseInfos != null) {
                        shop.setEnterpriseName(shopEnterpriseInfos.getName());
                    }
                }
            }
            return new Page<ShopDto>(shopPage.getCurrent(), shopPage.getSize(), shopPage.getTotal()).setRecords(records);
        } catch (Exception e) {
            log.error("ShopEnterpriseCertificatesServiceImpl.toShopExamineList 异常" + e.getMessage());
            CheckDataUtils.dataError("ShopEnterpriseCertificatesServiceImpl.toShopExamineList 异常");
        }
        return new Page<ShopDto>(1, toExamineListRequestModel.getPageSize());
    }

    @Override
    public String toShopExamineConfirm(Integer id, Integer status, String reason, Integer isLocked) {
        CheckDataUtils.judgeNull(id);
        Date now = new Date();
        Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>().eq("id", id).isNull("delete_time")
        );
        if (StringUtils.isEmpty(shop.getZfbName()) && StringUtils.isEmpty(shop.getZfbCode())) {
            throw new HzsxBizException("-1","支付宝信息不完善");
        }
        ShopEnterpriseInfos shopEnterpriseInfos = this.shopEnterpriseInfosDao.getOne(new QueryWrapper<ShopEnterpriseInfos>()
                .eq("shop_id", shop.getShopId())
                .isNull("delete_time")
        );
        if (shopEnterpriseInfos == null) {
            throw new HzsxBizException("-1","企业信息不完善");
        }
        shop.setId(id);
        shop.setUpdateTime(now);
        if(null != status){
            shop.setStatus(status);
            shop.setReason(reason);
            if (status.intValue() == ShopStatus.SHOP_STATUS_AUDIT_PASS.getCode()) {
                shop.setApprovalTime(now);
                shopSplitBillAccountDao.init(shop.getShopId(), shop.getName(), shopEnterpriseInfos.getName());
                shopEnterpriseInfosService.toShopEnterpriseExamineConform(shopEnterpriseInfos.getId(), ShopStatus.SHOP_ENTERPRISE_AUDIT_SUCCESS.getCode(), reason);
            } else {
                shopEnterpriseInfosService.toShopEnterpriseExamineConform(shopEnterpriseInfos.getId(), ShopStatus.SHOP_ENTERPRISE_AUDIT_PASS.getCode(), reason);
            }
        }
        if(null != isLocked){
            if (isLocked.intValue() == ShopStatus.SHOP_STATUS_IS_LOCK.getCode()) {
                //冻结
                shop.setLockedTime(now);
                shop.setIsDisabled(ShopStatus.SHOP_STATUS_IS_LOCK.getCode());
                shop.setIsLocked(ShopStatus.SHOP_STATUS_IS_LOCK.getCode());
            } else {
                shop.setIsDisabled(ShopStatus.SHOP_STATUS_NOT_LOCK.getCode());
                shop.setIsLocked(ShopStatus.SHOP_STATUS_NOT_LOCK.getCode());
            }
        }
        this.shopDao.updateById(shop);
        return shop.getShopId();
    }

    @Override
    public void lockedShop(String shopId, Integer status) {
        Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>().eq("shop_id", shopId).isNull("delete_time"));
        if (shop == null) {
            CheckDataUtils.dataError("店铺不存在");
        }
        Date now = new Date();
        shop.setUpdateTime(now);
        if (status.intValue() == ShopStatus.SHOP_STATUS_IS_LOCK.getCode()) {
            //冻结
            shop.setLockedTime(now);
            shop.setIsDisabled(ShopStatus.SHOP_STATUS_IS_LOCK.getCode());
            shop.setIsLocked(ShopStatus.SHOP_STATUS_IS_LOCK.getCode());
        } else {
            shop.setIsDisabled(ShopStatus.SHOP_STATUS_NOT_LOCK.getCode());
            shop.setIsLocked(ShopStatus.SHOP_STATUS_NOT_LOCK.getCode());
        }
        shopDao.updateById(shop);
    }

    @Override
    public void checkShop(String shopId) {
        Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>()
                .eq(StringUtils.isNotEmpty(shopId), "shop_id", shopId)
                .isNull("delete_time").orderByDesc("id").last("limit 1")
        );
        if(null == shop){
            throw new HzsxBizException("-1","商家不存在");
        }
        if(!ShopStatus.SHOP_STATUS_AUDIT_PASS.getCode().equals(shop.getStatus())){
            throw new HzsxBizException("-1","商家审核通过才可以添加商品，请联系运营管理员！！！");
        }
        if(!ShopStatus.SHOP_STATUS_NOT_LOCK.getCode().equals(shop.getIsLocked())){
            throw new HzsxBizException("-1","店铺信息被冻结，请联系运营管理员！！！");
        }
    }

    @Override
    public String selectShopServiceTelInfo(String shopId) {
        String serviceTel = null;
        Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>()
                .eq("shop_id", shopId)
                .isNull("delete_time")
                .last("limit 1")
        );
        if(null != shop){
            serviceTel = shop.getServiceTel();
        }
        return serviceTel;
    }

    @Override
    public Map<String, ShopDto> selectShopInfoByShopId(List<String> shopIdList) {
        Map<String, ShopDto> result = new HashMap<String, ShopDto>();
        if (CollectionUtils.isNotEmpty(shopIdList)) {
            shopIdList.forEach(shopId -> {
                Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>()
                    .eq(StringUtils.isNotEmpty(shopId), "shop_id", shopId)
                    .isNull("delete_time")
                );
                result.put(shopId, ShopConverter.model2Dto(shop));
            });
        }
        return result;
    }

    @Override
    public List<String> getShopIdListLikeName(String name) {
        return shopDao.list(new QueryWrapper<Shop>().select("shop_id").like("name",name)).stream().map(Shop::getShopId).collect(Collectors.toList());
    }


    @Override
    public List<ShopDto> listAllShop(String shopName) {
        List<Shop> shop = shopDao.list(new QueryWrapper<Shop>()
                .select("shop_id", "name").eq("status", 5).eq("is_locked", 0).eq("is_disabled", 0)
                .like(StringUtils.isNotEmpty(shopName), "name", shopName)
                .isNull("delete_time"));
        return ShopConverter.modelList2DtoList(shop);
    }



}