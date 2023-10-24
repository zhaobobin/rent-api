
package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.marketing.LiteCouponPackageConverter;
import com.rent.common.converter.marketing.LiteCouponTemplateConverter;
import com.rent.common.dto.marketing.CouponPackageDto;
import com.rent.common.dto.marketing.CouponPackageReqDto;
import com.rent.common.dto.marketing.CouponTemplateDto;
import com.rent.common.enums.marketing.CouponRangeConstant;
import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import com.rent.dao.marketing.LiteCouponPackageDao;
import com.rent.dao.marketing.LiteCouponTemplateDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.marketing.LiteCouponPackage;
import com.rent.model.marketing.LiteCouponTemplate;
import com.rent.model.marketing.LiteCouponTemplateRange;
import com.rent.service.marketing.LiteCouponPackageService;
import com.rent.service.marketing.LiteCouponTemplateRangeService;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 优惠券大礼包Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiteCouponPackageServiceImpl implements LiteCouponPackageService {

    private final LiteCouponPackageDao liteCouponPackageDao;
    private final LiteCouponTemplateDao liteCouponTemplateDao;
    private final LiteCouponTemplateRangeService liteCouponTemplateRangeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCouponPackage(CouponPackageDto request) {
        return addCouponPackage(request,null);
    }

    @Override
    public CouponPackageDto updateCouponPackagePageData(Long id) {
        LiteCouponPackage couponPackage = liteCouponPackageDao.getById(id);
        CouponPackageDto couponPackageDto = LiteCouponPackageConverter.model2Dto(couponPackage);
        packCouponPackageTemplateInfo(couponPackageDto);
        return couponPackageDto;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCouponPackage(Long id) {
        LiteCouponPackage couponPackage = liteCouponPackageDao.getById(id);
        //删除大礼包
        liteCouponPackageDao.deleteById(id);
        //恢复其名下的优惠券为未分配状态
        Set<Long> templateIdSet = new HashSet<>();
        for (String templateId : couponPackage.getTemplateIds().split(",")) {
            templateIdSet.add(Long.parseLong(templateId));
        }
        liteCouponTemplateDao.updateUnassigned(templateIdSet);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyCouponPackage(CouponPackageDto request) {
        LiteCouponPackage oldPackage = liteCouponPackageDao.getById(request.getId());
        //如果只是修改大礼包的基本信息，没有修改所包含的优惠券，那么就更新数据库
        if(request.getTemplateIds().equals(oldPackage.getTemplateIds())){
            LiteCouponPackage model = LiteCouponPackageConverter.dto2Model(request);
            model.setLeftNum(model.getNum());
            liteCouponPackageDao.updateById(model);
            Set<Long> templateIdSet = new HashSet<>();
            for (String templateId : model.getTemplateIds().split(",")) {
                templateIdSet.add(Long.parseLong(templateId));
            }
            liteCouponTemplateDao.updateAssigned(templateIdSet,model);
        }else{
            deleteCouponPackage(request.getId());
            request.setId(null);
            addCouponPackage(request,oldPackage);
        }
        return Boolean.TRUE;
    }

    @Override
    public Page<CouponPackageDto> queryCouponPackagePage(CouponPackageReqDto request) {
        Page<LiteCouponPackage> page = liteCouponPackageDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
            new QueryWrapper<LiteCouponPackage>()
                .eq("source_shop_id",request.getSourceShopId())
                .eq("channel_id",request.getChannelId())
                .eq(StringUtil.isNotEmpty(request.getType().getCode()),"type",request.getType())
                .like(StringUtil.isNotEmpty(request.getName()),"name",request.getName())
                .eq(StringUtil.isNotEmpty(request.getStatus()),"status",request.getStatus())
                .isNull("delete_time")
                .orderByDesc("id")
        );

        List<CouponPackageDto> dtoList = LiteCouponPackageConverter.modelList2DtoList(page.getRecords());
        for (CouponPackageDto couponPackageDto : dtoList) {
            packCouponPackageTemplateInfo(couponPackageDto);
        }
        return new Page<CouponPackageDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(dtoList);
    }

    /**
     * 添加大礼包
     * @param request 前端的请求
     * @param oldPackage 如果是编辑，需要将原来的 大礼包信息 传递过来
     * @return
     */
    private Long addCouponPackage(CouponPackageDto request,LiteCouponPackage oldPackage){
        LiteCouponPackage model = LiteCouponPackageConverter.dto2Model(request);
        String[] templateIds = model.getTemplateIds().split(",");
        Set<Long> templateIdSet = new HashSet<>();
        for (String templateId : templateIds) {
            templateIdSet.add(Long.parseLong(templateId));
        }
        List<LiteCouponTemplate> templates =  liteCouponTemplateDao.getByIds(templateIdSet);
        Date now = new Date();
        Set<String> sceneSet = new HashSet<>(3);
        for (LiteCouponTemplate template : templates) {
            if(template.getDeleteTime()!=null){
                throw new HzsxBizException("-1","优惠券已经被删除，优惠券ID="+template.getId());
            }
            if(template.getPackageId()!=null && template.getPackageId()!=0){
                throw new HzsxBizException("-1","优惠券已经绑定大礼包，优惠券ID="+template.getId());
            }
            if(template.getType()!= CouponTemplateTypeEnum.PACKAGE){
                throw new HzsxBizException("-1","优惠券非大礼包优惠券，优惠券ID="+template.getId());
            }
            sceneSet.add(template.getScene());
        }
        model.setContainScene(LiteCouponPackageConverter.getContainScene(sceneSet));
        model.setLeftNum(model.getNum());
        model.setCreateTime(now);
        if(oldPackage!=null){
            model.setBindId(oldPackage.getBindId()==null ? oldPackage.getId() : oldPackage.getBindId());
        }
        liteCouponPackageDao.save(model);
        if(model.getBindId()==null){
            model.setBindId(model.getId());
            liteCouponPackageDao.updateById(model);
        }
        liteCouponTemplateDao.updateAssigned(templateIdSet,model);
        return model.getId();
    }

    /**
     * 封装大礼包下的优惠券信息
     * @param couponPackageDto
     */
    private void packCouponPackageTemplateInfo(CouponPackageDto couponPackageDto){
        Set<Long> templateIdSet = new HashSet<>();
        for (String templateId : couponPackageDto.getTemplateIds().split(",")) {
            templateIdSet.add(Long.parseLong(templateId));
        }
        List<CouponTemplateDto> couponTemplateDtoList  = LiteCouponTemplateConverter.modelList2DtoList(liteCouponTemplateDao.getByIds(templateIdSet));
        for (CouponTemplateDto couponTemplateDto : couponTemplateDtoList) {
            List<LiteCouponTemplateRange> couponTemplateRanges =  liteCouponTemplateRangeService.getCouponTemplateRangeByTemplateId(couponTemplateDto.getId());
            String rangeType = couponTemplateRanges.get(0).getType();
            String rangeStr = CouponRangeConstant.RANGE_TYPE_ALL.equals(rangeType) ? "全部商品可用":"部分商品可用";
            couponTemplateDto.setRangeStr(rangeStr);
        }
        couponPackageDto.setCouponTemplateList(couponTemplateDtoList);
    }

}