package com.rent.service.marketing.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.constant.CouponPackageConstant;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.converter.marketing.LiteCouponTemplateConverter;
import com.rent.common.dto.marketing.*;
import com.rent.common.dto.product.ProductCouponDto;
import com.rent.common.enums.marketing.CouponRangeConstant;
import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import com.rent.common.enums.marketing.UserCouponConstant;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.dao.marketing.LiteCouponPackageDao;
import com.rent.dao.marketing.LiteCouponTemplateDao;
import com.rent.dao.marketing.LiteCouponTemplateRangeDao;
import com.rent.dao.marketing.LiteUserCouponDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.marketing.LiteCouponPackage;
import com.rent.model.marketing.LiteCouponTemplate;
import com.rent.model.marketing.LiteCouponTemplateRange;
import com.rent.model.marketing.LiteUserCoupon;
import com.rent.service.marketing.LiteCouponCenterService;
import com.rent.util.DateUtil;
import com.rent.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优惠券中心
 *
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiteCouponCenterServiceImpl implements LiteCouponCenterService {

    private final LiteCouponPackageDao liteCouponPackageDao;
    private final LiteUserCouponDao liteUserCouponDao;
    private final LiteCouponTemplateDao liteCouponTemplateDao;
    private final LiteCouponTemplateRangeDao liteCouponTemplateRangeDao;

    @Override
    public LiteCouponCenterCouponDto index(String uid, Boolean isNewUser, String scene) {
        LiteCouponCenterCouponDto dto = new LiteCouponCenterCouponDto();
        //获取到优惠券模板
        List<LiteCouponTemplate> couponTemplateList = liteCouponTemplateDao.getAvailableList(isNewUser,scene);
        List<CouponTemplateIndexDto> templateIndexDtoList = new ArrayList<>(couponTemplateList.size());
        couponTemplateList.stream()
            .forEach(couponTemplate -> {
                if (isInRange(couponTemplate)) {
                    Integer userCouponUnusedCount = liteUserCouponDao.getUserCouponUnusedCount(uid, couponTemplate.getId());
                    CouponTemplateIndexDto templateIndexDto = LiteCouponTemplateConverter.model2IndexDto(couponTemplate);
                    templateIndexDto.setIsBind(userCouponUnusedCount.intValue()>0);
                    templateIndexDto.setRangeStr(liteCouponTemplateRangeDao.getCouponRangeType(couponTemplate.getId()));
                    templateIndexDtoList.add(templateIndexDto);
                }
            });
        dto.setCouponList(templateIndexDtoList);
        List<LiteCouponPackage> couponPackageList = liteCouponPackageDao.getAvailableList(isNewUser);
        dto.setHasCouponPackage(couponPackageList.size()>0);
        return dto;
    }

    @Override
    public List<LiteCouponCenterPackageDto> indexPackage(String uid, Boolean isNewUser) {
        List<LiteCouponPackage> couponPackageList = liteCouponPackageDao.getAvailableList(isNewUser);
        if(CollectionUtils.isEmpty(couponPackageList)){
            return Collections.emptyList();
        }
        List<Long> packageIdList = couponPackageList.stream().map(LiteCouponPackage::getId).collect(Collectors.toList());
        Map<Long,List<LiteCouponTemplate>> templateMap = liteCouponTemplateDao.getByPackageIdList(packageIdList);
        List<LiteCouponCenterPackageDto> dtoList = new ArrayList<>(couponPackageList.size());
        for (LiteCouponPackage couponPackage : couponPackageList) {
            LiteCouponCenterPackageDto dto = new LiteCouponCenterPackageDto();
            String[] couponTemplateIds = couponPackage.getTemplateIds().split(",");
            Integer userPackageCouponCount = liteUserCouponDao.getUserPackageCount(uid, couponPackage.getId());
            dto.setIsBind(couponTemplateIds.length * couponPackage.getUserLimitNum() == userPackageCouponCount);
            dto.setPackageName(couponPackage.getName());
            dto.setId(couponPackage.getId());
            List<LiteCouponTemplate> templateList = templateMap.get(couponPackage.getId());
            List<CouponTemplateIndexDto> couponTemplateIndexDtoList = new ArrayList<>(templateList.size());
            for (LiteCouponTemplate liteCouponTemplate : templateList) {
                CouponTemplateIndexDto templateIndexDto = LiteCouponTemplateConverter.model2IndexDto(liteCouponTemplate);
                couponTemplateIndexDtoList.add(templateIndexDto);
                templateIndexDto.setRangeStr(liteCouponTemplateRangeDao.getCouponRangeType(liteCouponTemplate.getId()));
            }
            dto.setCouponList(couponTemplateIndexDtoList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<MyCouponDto> my(String uid, String scene) {
        List<LiteUserCoupon> userCoupons = liteUserCouponDao.getUserUnusedCoupon(uid);
        List<MyCouponDto> dtoList = new ArrayList<>(userCoupons.size());
        Long  now = System.currentTimeMillis();
        for (LiteUserCoupon userCoupon : userCoupons) {
            if(userCoupon.getDiscountAmount().compareTo(BigDecimal.ZERO)<=0){
                continue;
            }
            LiteCouponTemplate couponTemplate = liteCouponTemplateDao.getById(userCoupon.getTemplateId());
            if(now<userCoupon.getStartTime().getTime()){
                continue;
            }
            if(now>userCoupon.getEndTime().getTime()){
                userCoupon.setStatus(UserCouponConstant.STATUS_INVALID);
                liteUserCouponDao.updateById(userCoupon);
                continue;
            }
            if(CouponTemplateConstant.SCENE_RENT.equals(scene)){
                if(CouponTemplateConstant.SCENE_BUY_OUT.equals(couponTemplate.getScene())){
                    //查询租赁但是模板时买断
                    continue;
                }
            }else{
                if(!CouponTemplateConstant.SCENE_BUY_OUT.equals(couponTemplate.getScene())){
                    //查询买断但是模板不是买断
                    continue;
                }
            }
            addMyCouponDtoToList(userCoupon,couponTemplate,dtoList);
        }
        return dtoList;
    }

    @Override
    public List<MyCouponDto> myUsed(String uid) {
        List<LiteUserCoupon> userCoupons = liteUserCouponDao.getUserUsedCoupon(uid);
        List<MyCouponDto> dtoList = new ArrayList<>(userCoupons.size());
        for (LiteUserCoupon userCoupon : userCoupons) {
            LiteCouponTemplate couponTemplate = liteCouponTemplateDao.getById(userCoupon.getTemplateId());
            addMyCouponDtoToList(userCoupon,couponTemplate,dtoList);
        }
        return dtoList;
    }

    @Override
    public List<MyCouponDto> myExpire(String uid) {
        List<LiteUserCoupon> userCoupons = liteUserCouponDao.getUserExpireCoupon(uid);
        List<MyCouponDto> dtoList = new ArrayList<>(userCoupons.size());
        for (LiteUserCoupon userCoupon : userCoupons) {
            LiteCouponTemplate couponTemplate = liteCouponTemplateDao.getById(userCoupon.getTemplateId());
            addMyCouponDtoToList(userCoupon,couponTemplate,dtoList);
        }
        return dtoList;
    }

    private void addMyCouponDtoToList(LiteUserCoupon userCoupon,LiteCouponTemplate couponTemplate,List<MyCouponDto> dtoList){
        MyCouponDto dto = new MyCouponDto();
        dto.setId(userCoupon.getId());
        dto.setTemplateId(userCoupon.getTemplateId());
        dto.setDiscountAmount(userCoupon.getDiscountAmount());
        dto.setStartTime(userCoupon.getStartTime());
        dto.setEndTime(userCoupon.getEndTime());
        dto.setMinAmount(couponTemplate.getMinAmount());
        dto.setRangeStr(liteCouponTemplateRangeDao.getCouponRangeType(couponTemplate.getId()));
        dto.setTitle(couponTemplate.getTitle());
        dtoList.add(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindCoupon(String uid, String phone, Long templateId, Boolean isNewUser) {
        LiteCouponTemplate couponTemplate = liteCouponTemplateDao.selectByBindIdForUpdate(templateId);
        if (couponTemplate == null) {
            throw new HzsxBizException("-1", "未查询到优惠券");
        }
        if (CouponTemplateConstant.STATUS_INVALID.equals(couponTemplate.getStatus())) {
            throw new HzsxBizException("-1", "优惠券已经失效");
        }
        if (couponTemplate.getType() == CouponTemplateTypeEnum.PACKAGE) {
            throw new HzsxBizException("-1", "该优惠券为大礼包优惠券，不能单独领取");
        }
        if (!isInRange(couponTemplate)) {
            throw new HzsxBizException("-1", "优惠券不在有效期");
        }
        if (CouponTemplateConstant.STATUS_RUN_OUT.equals(couponTemplate.getStatus())
            || couponTemplate.getLeftNum() == 0) {
            throw new HzsxBizException("-1", "数量不足");
        }

        Integer userCouponCouponCount = liteUserCouponDao.getUserCouponCount(uid, templateId);
        if (userCouponCouponCount >= couponTemplate.getUserLimitNum()) {
            throw new HzsxBizException("-1", "超过领取数量");
        }
        //是老用户并且券是新用户专享的
        if (CouponTemplateConstant.FOR_NEW_T.equals(couponTemplate.getForNew())) {
            if (!isNewUser) {
                throw new HzsxBizException("-1", "该券为新用户专享");
            }
        }

        bindUserCoupon(uid, phone, UserCouponConstant.BIND_TYPE_REQUEST, couponTemplate, null, null);
        couponTemplate.setLeftNum(couponTemplate.getLeftNum() - 1);
        couponTemplate.setUpdateTime(new Date());
        if (couponTemplate.getLeftNum() == 0) {
            couponTemplate.setStatus(CouponTemplateConstant.STATUS_RUN_OUT);
        }
        liteCouponTemplateDao.updateById(couponTemplate);
        return "领取成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindCouponPackage(String uid, String phone, Long packageId, Boolean isNewUser) {
        LiteCouponPackage couponPackage = liteCouponPackageDao.selectByBindIdForUpdate(packageId);
        if (couponPackage == null) {
            throw new HzsxBizException("-1", "未查询到大礼包信息");
        }
        if (CouponPackageConstant.STATUS_INVALID.equals(couponPackage.getForNew())) {
            throw new HzsxBizException("-1", "该礼包已经失效");
        }
        if (CouponPackageConstant.STATUS_RUN_OUT.equals(couponPackage.getStatus()) || couponPackage.getLeftNum() == 0) {
            throw new HzsxBizException("-1", "数量不足");
        }

        String[] couponTemplateIds = couponPackage.getTemplateIds()
            .split(",");
        Integer userPackageCouponCount = liteUserCouponDao.getUserPackageCount(uid, packageId);
        if (couponTemplateIds.length * couponPackage.getUserLimitNum() <= userPackageCouponCount) {
            throw new HzsxBizException("-1", "超过领取数量");
        }
        if (CouponTemplateConstant.FOR_NEW_T.equals(couponPackage.getForNew())) {
            if (!isNewUser) {
                throw new HzsxBizException("-1", "该大礼包为新用户专享");
            }
        }
        for (String templateId : couponTemplateIds) {
            LiteCouponTemplate couponTemplate = liteCouponTemplateDao.selectByBindIdForUpdate(Long.parseLong(templateId));
            bindUserCoupon(uid, phone, UserCouponConstant.BIND_TYPE_REQUEST, couponTemplate, couponPackage.getName(),
                couponPackage.getId());
        }
        couponPackage.setUpdateTime(new Date());
        couponPackage.setLeftNum(couponPackage.getLeftNum() - 1);
        if (couponPackage.getLeftNum() == 0) {
            couponPackage.setStatus(CouponTemplateConstant.STATUS_RUN_OUT);
        }
        liteCouponPackageDao.updateById(couponPackage);
        return "领取成功";
    }

    @Override
    public List<ProductCouponDto> getProductCoupon(String categoryId, String productId,String shopId, String uid, Boolean isNewUser) {
        Map<Long, String> templateIdMap = getInRangeCouponTemplate(categoryId, productId,shopId);
        List<LiteCouponTemplate> couponTemplateList = liteCouponTemplateDao.getByIds(templateIdMap.keySet());

        List<LiteUserCoupon> userAllCouponList = liteUserCouponDao.getUserCoupon(uid);
        Map<Long,List<LiteUserCoupon>> userAllCouponMap = userAllCouponList.stream().collect(Collectors.groupingBy(LiteUserCoupon::getTemplateId));
        List<ProductCouponDto> productCouponDtoList = new ArrayList<>(couponTemplateList.size());
        for (LiteCouponTemplate couponTemplate : couponTemplateList) {
            //优惠金额为0，表明是延期券，不展示在商品页面
            if(couponTemplate.getDiscountAmount().compareTo(BigDecimal.ZERO)<=0){
                continue;
            }
            //totalCount：用户拥有该券的数量     unusedCount：用户拥有该券并且未使用的数量
            Integer totalCount=0;
            Integer unusedCount = 0;
            List<LiteUserCoupon> userCoupons = userAllCouponMap.get(couponTemplate.getId());
            if(CollectionUtil.isNotEmpty(userCoupons)){
                totalCount = userCoupons.size();
                for (LiteUserCoupon userCoupon : userCoupons) {
                    if(UserCouponConstant.STATUS_UNUSED.equals(userCoupon.getStatus())){
                        unusedCount++;
                    }
                }
            }
            // 用户没有未使用的该券  && （优惠券不是有效状态 || 优惠券已经删除 || 该券不是独立使用的券 || 拥有的数量和用户限制的数量一致）--就不用展示这张优惠券了
            if (unusedCount==0) {
                if (!CouponTemplateConstant.STATUS_VALID.equals(couponTemplate.getStatus())
                    || couponTemplate.getDeleteTime() != null) {
                    continue;
                }
                if (couponTemplate.getType() != CouponTemplateTypeEnum.SINGLE && StringUtils.isEmpty(couponTemplate.getBindUrl())) {
                    continue;
                }
                if (!isInRange(couponTemplate)) {
                    continue;
                }
                if(totalCount.equals(couponTemplate.getUserLimitNum())){
                    continue;
                }
            }
            //如果券是针对新用户的但是用户不是新用户，也就不展示了
            if (!isNewUser && couponTemplate.getForNew().equals(CouponTemplateConstant.FOR_NEW_T)) {
                continue;
            }
            ProductCouponDto dto = LiteCouponTemplateConverter.model2ProductCouponDto(couponTemplate);
            dto.setBind(unusedCount>0);
            dto.setRangeStr(
                CouponRangeConstant.RANGE_TYPE_ALL.equals(templateIdMap.get(dto.getTemplateId())) ? "全部商品可用" :
                    "部分商品可用");
            productCouponDtoList.add(dto);
        }
        return productCouponDtoList;
    }

    @Override
    public List<OrderCouponDto> getOrderCoupon(String categoryId, String productId, String shopId, String uid,
                                               BigDecimal totalAmount, BigDecimal firstAmount, EnumOrderType orderType) {

        //获取商品可以使用的优惠券ID
        Map<Long, String> templateIdMap = getInRangeCouponTemplate(categoryId, productId,shopId);
        List<LiteCouponTemplate> couponTemplateList = liteCouponTemplateDao.getByIds(templateIdMap.keySet());
        //过滤掉金额和使用场景不符合的优惠券信息
        Map<Long, LiteCouponTemplate> map = couponTemplateList.stream()
                .filter(couponTemplate -> {
                    //优惠金额为0，表明是延期券，不展示在订单详情
                    if(couponTemplate.getDiscountAmount().compareTo(BigDecimal.ZERO)<=0){
                        return false;
                    }
                    if (orderType.equals(EnumOrderType.GENERAL_ORDER)) {
                        if (couponTemplate.getScene()
                                .equals(CouponTemplateConstant.SCENE_RENT) && totalAmount.compareTo(
                                couponTemplate.getMinAmount()) > 0) {
                            return true;
                        } else if (couponTemplate.getScene()
                                .equals(CouponTemplateConstant.SCENE_FIRST) && firstAmount.compareTo(
                                couponTemplate.getMinAmount()) > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (orderType.equals(EnumOrderType.BUYOUT_ORDER)) {
                        if (couponTemplate.getScene()
                                .equals(CouponTemplateConstant.SCENE_BUY_OUT) && totalAmount.compareTo(
                                couponTemplate.getMinAmount()) > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                })
                .collect(Collectors.toMap(LiteCouponTemplate::getId, Function.identity()));

        //获取到用户所拥有的优惠券
        List<LiteUserCoupon> userCoupons = liteUserCouponDao.getUserUsableCoupon(uid);
        //过滤用户拥有的但是不符合条件的优惠券
        userCoupons = userCoupons.stream()
                .filter(userCoupon -> map.containsKey(userCoupon.getTemplateId()))
                .collect(Collectors.toList());

        List<OrderCouponDto> orderCouponDtoList = new ArrayList<>(userCoupons.size());
        for (LiteUserCoupon userCoupon : userCoupons) {
            OrderCouponDto orderCouponDto = new OrderCouponDto();
            orderCouponDto.setCode(userCoupon.getCode());
            orderCouponDto.setStartTime(userCoupon.getStartTime());
            orderCouponDto.setEndTime(userCoupon.getEndTime());
            orderCouponDto.setScene(map.get(userCoupon.getTemplateId())
                    .getScene());
            orderCouponDto.setDiscountAmount(map.get(userCoupon.getTemplateId())
                    .getDiscountAmount());
            orderCouponDto.setMinAmount(map.get(userCoupon.getTemplateId())
                    .getMinAmount());
            orderCouponDto.setCheck(Boolean.FALSE);
            orderCouponDtoList.add(orderCouponDto);
        }
        return orderCouponDtoList;
    }


    @Override
    public List<OrderCouponDto> getOrderExtensionCoupon(String categoryId, String productId, String shopId, String uid) {

        //获取商品可以使用的优惠券ID
        Map<Long, String> templateIdMap = getInRangeCouponTemplate(categoryId, productId,shopId);
        List<LiteCouponTemplate> couponTemplateList = liteCouponTemplateDao.getByIds(templateIdMap.keySet());
        Map<Long, LiteCouponTemplate> map = couponTemplateList.stream()
                .filter(e -> e.getDiscountAmount().compareTo(BigDecimal.ZERO)<0)
                .collect(Collectors.toMap(LiteCouponTemplate::getId, Function.identity()));

        //获取到用户所拥有的优惠券
        List<LiteUserCoupon> userCoupons = liteUserCouponDao.getUserUsableCoupon(uid);
        //过滤用户拥有的但是不符合条件的优惠券
        userCoupons = userCoupons.stream()
                .filter(userCoupon -> map.containsKey(userCoupon.getTemplateId()))
                .collect(Collectors.toList());

        List<OrderCouponDto> orderCouponDtoList = new ArrayList<>(userCoupons.size());
        for (LiteUserCoupon userCoupon : userCoupons) {
            OrderCouponDto orderCouponDto = new OrderCouponDto();
            orderCouponDto.setCode(userCoupon.getCode());
            orderCouponDto.setStartTime(userCoupon.getStartTime());
            orderCouponDto.setEndTime(userCoupon.getEndTime());
            orderCouponDto.setScene(map.get(userCoupon.getTemplateId())
                    .getScene());
            orderCouponDto.setDiscountAmount(map.get(userCoupon.getTemplateId())
                    .getDiscountAmount());
            orderCouponDto.setMinAmount(map.get(userCoupon.getTemplateId())
                    .getMinAmount());
            orderCouponDtoList.add(orderCouponDto);
        }
        return orderCouponDtoList;
    }

    @Override
    public Boolean updateCouponUnused(String orderId) {
        List<LiteUserCoupon> userCoupons = liteUserCouponDao.getUserCouponByOrderId(orderId);
        for (LiteUserCoupon userCoupon : userCoupons) {
            if (userCoupon == null) {
                return Boolean.TRUE;
            }
            if (userCoupon.getReceiveType()
                    .equals(UserCouponConstant.BIND_TYPE_ALIPAY)) {
                return Boolean.TRUE;
            }
            liteUserCouponDao.updateCouponUnused(userCoupon.getCode());
        }
        return Boolean.TRUE;
    }

    @Override
    public OrderCouponDto getCouponInfoByCode(String categoryId, String productId, String shopId, String uid,
        BigDecimal totalAmount, BigDecimal firstAmount, EnumOrderType orderType, String code) {
        LiteUserCoupon coupon = liteUserCouponDao.getUserCouponByCode(code);

        if (coupon == null || !coupon.getUid()
            .equals(uid)) {
            throw new HzsxBizException("-1", "为查询到优惠券");
        }
        if (!UserCouponConstant.STATUS_UNUSED.equals(coupon.getStatus())) {
            throw new HzsxBizException("-1", "优惠券状态错误");
        }

        //判断使用条件 租赁优惠券和买断优惠券根据总金额判断 ，首期优惠券根据首期租金判断
        LiteCouponTemplate couponTemplate = liteCouponTemplateDao.getById(coupon.getTemplateId());
        if (couponTemplate.getScene()
            .equals(CouponTemplateConstant.SCENE_RENT) || couponTemplate.getScene()
            .equals(CouponTemplateConstant.SCENE_BUY_OUT)) {
            if (totalAmount.compareTo(couponTemplate.getMinAmount()) < 0) {
                throw new HzsxBizException("-1", "最低使用金额不匹配");
            }
        } else if (couponTemplate.getScene()
            .equals(CouponTemplateConstant.SCENE_FIRST)) {
            if (firstAmount.compareTo(couponTemplate.getMinAmount()) < 0) {
                throw new HzsxBizException("-1", "最低使用金额不匹配");
            }
        } else {
            throw new HzsxBizException("-1", "系统异常");
        }

        //判断是否在有效时间内
        Date now = new Date();
        if (coupon.getStartTime()
            .after(now) || coupon.getEndTime()
            .before(now)) {
            liteUserCouponDao.updateCouponInvalid(coupon.getId());
            throw new HzsxBizException("-1", "超出使用范围");
        }

        //判断使用范围
        CouponRangeDto range = liteCouponTemplateRangeDao.getCouponRange(coupon.getTemplateId());
        if (range.getType()
            .equals(CouponRangeConstant.RANGE_TYPE_ALL)) {

        } else if (range.getType()
            .equals(CouponRangeConstant.RANGE_TYPE_CATEGORY)) {
            if (!range.getValue()
                .contains(categoryId)) {
                throw new HzsxBizException("-1", "不在使用范围");
            }
        } else if (range.getType()
            .equals(CouponRangeConstant.RANGE_TYPE_PRODUCT)) {
            if (!range.getValue()
                .contains(productId)) {
                throw new HzsxBizException("-1", "不在使用范围");
            }
        } else if (range.getType()
            .equals(CouponRangeConstant.RANGE_TYPE_SHOP)) {
            if (!range.getValue()
                .contains(shopId)) {
                throw new HzsxBizException("-1", "不在使用范围");
            }
        } else {
            return null;
        }

        OrderCouponDto dto = new OrderCouponDto();
        dto.setCode(coupon.getCode());
        dto.setStartTime(coupon.getStartTime());
        dto.setEndTime(coupon.getEndTime());
        dto.setScene(couponTemplate.getScene());
        dto.setDiscountAmount(coupon.getDiscountAmount());
        dto.setMinAmount(couponTemplate.getMinAmount());
        return dto;
    }

    @Override
    public CouponRangeDto getCouponRange(Long templateId) {
        return liteCouponTemplateRangeDao.getCouponRange(templateId);
    }

    /**
     * 获取范围内的优惠券模板ID
     *
     * @param categoryId
     * @param productId
     * @return
     */
    private Map<Long, String> getInRangeCouponTemplate(String categoryId, String productId,String shopId) {
        //获取该商品类目可用的优惠券
        List<LiteCouponTemplateRange> categoryTemplateIds = liteCouponTemplateRangeDao.getInRangeTemplateId(
            CouponRangeConstant.RANGE_TYPE_CATEGORY, categoryId);
        //获取该商品可用的优惠券
        List<LiteCouponTemplateRange> productTemplateIds = liteCouponTemplateRangeDao.getInRangeTemplateId(
            CouponRangeConstant.RANGE_TYPE_PRODUCT, productId);

        //获取该商品所属店铺可用的优惠券
        List<LiteCouponTemplateRange> shopTemplateIds = liteCouponTemplateRangeDao.getInRangeTemplateId(
                CouponRangeConstant.RANGE_TYPE_SHOP, shopId);
        //获取所有商品都可用的优惠券
        List<LiteCouponTemplateRange> allTemplateIds = liteCouponTemplateRangeDao.getInRangeTemplateId(
            CouponRangeConstant.RANGE_TYPE_ALL, CouponRangeConstant.RANGE_TYPE_ALL);

        List<LiteCouponTemplateRange> list = new ArrayList<>();
        list.addAll(productTemplateIds);
        list.addAll(categoryTemplateIds);
        list.addAll(shopTemplateIds);
        list.addAll(allTemplateIds);
        Map<Long, String> map = list.stream()
            .collect(
                Collectors.toMap(LiteCouponTemplateRange::getTemplateId, LiteCouponTemplateRange::getType));
        return map;
    }

    /**
     * 给用户添加一张优惠券
     *
     * @param uid
     * @param phone
     * @param receiveType
     * @param couponTemplate
     * @param packageName
     * @return
     */
    @Override
    public Boolean bindUserCoupon(String uid, String phone, String receiveType, LiteCouponTemplate couponTemplate,
        String packageName, Long packageId) {
        LiteUserCoupon userCoupon = new LiteUserCoupon();
        Date date = new Date();
        if (couponTemplate.getDelayDayNum() != null) {
            userCoupon.setStartTime(date);
            userCoupon.setEndTime(DateUtil.addDate(date, couponTemplate.getDelayDayNum()));
        } else {
            userCoupon.setStartTime(couponTemplate.getStartTime());
            userCoupon.setEndTime(couponTemplate.getEndTime());
        }
        userCoupon.setTemplateId(couponTemplate.getId())
            .setPackageName(packageName)
            .setPackageId(packageId)
            .setDiscountAmount(couponTemplate.getDiscountAmount())
            .setPhone(phone)
            .setCode(RandomUtil.randomString(32))
            .setUid(uid)
            .setReceiveType(receiveType)
            .setStatus(UserCouponConstant.STATUS_UNUSED)
            .setChannelId(couponTemplate.getChannelId())
            .setReceiveTime(date);
        liteUserCouponDao.save(userCoupon);
        return Boolean.TRUE;
    }


    @Override
    public Boolean updateCouponUsed(String code, String orderId) {
        LiteUserCoupon userCoupon = liteUserCouponDao.getUserCouponByCode(code);
        userCoupon.setOrderId(orderId);
        userCoupon.setUseTime(new Date());
        userCoupon.setStatus(UserCouponConstant.STATUS_USED);
        liteUserCouponDao.updateById(userCoupon);
        return Boolean.TRUE;
    }


    /**
     * 判断优惠券模板是否在有效期
     *
     * @param couponTemplate
     * @return 在有效期返回true，不在返回false
     */
    private boolean isInRange(LiteCouponTemplate couponTemplate) {
        if (couponTemplate.getDelayDayNum() == null) {
            if (!DateUtil.isInRange(new Date(), couponTemplate.getStartTime(), couponTemplate.getEndTime())) {
                return false;
            }
        }
        return true;
    }
}