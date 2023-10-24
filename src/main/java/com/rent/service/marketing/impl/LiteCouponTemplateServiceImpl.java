        
package com.rent.service.marketing.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.converter.marketing.LiteCouponTemplateConverter;
import com.rent.common.dto.marketing.*;
import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.enums.marketing.CouponRangeConstant;
import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import com.rent.common.enums.marketing.UserCouponConstant;
import com.rent.common.util.SequenceTool;
import com.rent.config.outside.OutsideConfig;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.marketing.LiteCouponTemplateDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.marketing.LiteCouponTemplate;
import com.rent.model.marketing.LiteCouponTemplateRange;
import com.rent.service.marketing.LiteCouponCenterService;
import com.rent.service.marketing.LiteCouponTemplateRangeService;
import com.rent.service.marketing.LiteCouponTemplateService;
import com.rent.service.marketing.LiteUserCouponService;
import com.rent.service.product.PlatformChannelService;
import com.rent.common.util.OSSFileUtils;
import com.rent.util.RandomUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 优惠券模版Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiteCouponTemplateServiceImpl implements LiteCouponTemplateService {

    private final LiteCouponTemplateDao liteCouponTemplateDao;
    private final LiteCouponTemplateRangeService liteCouponTemplateRangeService;
    private final LiteUserCouponService liteUserCouponService;
    private final LiteCouponCenterService liteCouponCenterService;
    private final PlatformChannelService platformChannelService;
    private final OSSFileUtils ossFileUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCouponTemplate(AddOrUpdateCouponTemplateDto request) {
        return saveCouponTemplate(request,null);
     }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyCouponTemplate(AddOrUpdateCouponTemplateDto request) {
        Long oldTemplateId = request.getId();
        LiteCouponTemplate originModel = liteCouponTemplateDao.getById(oldTemplateId);
        //如果是已经分配给大礼包的优惠券
        if(originModel.getPackageId()!=null){
            throw new HzsxBizException("-1","该优惠券不能编辑，已经分配给大礼包");
        }
        //优惠券已经删除
        if(originModel.getDeleteTime()!=null){
            throw new HzsxBizException("-1","优惠券已经被删除");
        }
        //获取到旧的优惠券ID，更新为历史记录
        liteCouponTemplateDao.updateHistory(oldTemplateId);
        //保存优惠券信息
        saveCouponTemplate(request,originModel);
        return Boolean.TRUE;
    }

    @Override
    public AddOrUpdateCouponTemplateDto getCouponTemplateUpdatePageData(Long id) {
        LiteCouponTemplate model = liteCouponTemplateDao.getById(id);

        AddOrUpdateCouponTemplateDto dto = LiteCouponTemplateConverter.modelToAddOrUpdateCouponTemplateDto(model);
        List<LiteCouponTemplateRange> couponTemplateRanges =  liteCouponTemplateRangeService.getCouponTemplateRangeByTemplateId(id);

        String rangeType = couponTemplateRanges.get(0).getType();
        if(CouponRangeConstant.RANGE_TYPE_SHOP.equals(rangeType) || CouponRangeConstant.RANGE_TYPE_ALL.equals(rangeType)){
            dto.setRangeType(CouponRangeConstant.RANGE_TYPE_ALL);
        }else{
            dto.setRangeType(rangeType);
            List<CouponRangeReqDto> rangeList = new ArrayList<>(couponTemplateRanges.size());
            for (LiteCouponTemplateRange couponTemplateRange : couponTemplateRanges) {
                CouponRangeReqDto reqDto = new CouponRangeReqDto();
                reqDto.setValue(couponTemplateRange.getValue());
                reqDto.setDescription(couponTemplateRange.getDescription());
                rangeList.add(reqDto);
            }
            dto.setRangeList(rangeList);
        }

        List<String> phones = liteUserCouponService.getAssignRecord(id);
        if(CollectionUtil.isNotEmpty(phones)){
            dto.setUserRange(CouponTemplateConstant.RANG_USER_PART);
            dto.setPhones(phones);
        }else{
            if(CouponTemplateConstant.FOR_NEW_T.equals(model.getForNew())){
                dto.setUserRange(CouponTemplateConstant.RANG_USER_NEW);
            }else {
                dto.setUserRange(CouponTemplateConstant.RANG_USER_ALL);
            }
        }
        dto.setForNew(null);
        return dto;
    }

    @Override
    public CouponTemplatePageDto getCouponTemplateDetail(Long id) {
        CouponTemplatePageDto dto = new CouponTemplatePageDto();
        LiteCouponTemplate model = liteCouponTemplateDao.getById(id);
        Integer bindCount = liteUserCouponService.getBindCount(id);
        Integer usedCount = liteUserCouponService.getUsedCount(id);
        List<LiteCouponTemplateRange> couponTemplateRanges =  liteCouponTemplateRangeService.getCouponTemplateRangeByTemplateId(id);
        String rangeType = couponTemplateRanges.get(0).getType();
        String rangeStr = CouponRangeConstant.RANGE_TYPE_ALL.equals(rangeType) ? "全部商品可用":"部分商品可用";
        dto.setId(id);
        dto.setBindId(model.getBindId());
        dto.setTitle(model.getTitle());
        dto.setScene(model.getScene());
        dto.setRangeStr(rangeStr);
        dto.setMinAmount(model.getMinAmount());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setStatus(model.getStatus());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setDelayDayNum(model.getDelayDayNum());
        dto.setNum(model.getNum());
        dto.setBindNum(bindCount);
        dto.setLeftNum(model.getLeftNum());
        dto.setUsedNum(usedCount);
        dto.setUnusedNum(bindCount - usedCount);
        dto.setDeleteTime(model.getDeleteTime());
        dto.setType(model.getType());
        return dto;
    }

    @Override
    public Boolean deleteCouponTemplate(Long id) {
        LiteCouponTemplate model = liteCouponTemplateDao.getById(id);
        //如果是已经分配给大礼包的优惠券
        if(model.getPackageId()!=null){
            throw new HzsxBizException("-1","该优惠券不能删除，已经分配给大礼包");
        }
        liteCouponTemplateDao.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * 保存优惠券信息
     * 1.保存优惠券信息
     * 2.保存优惠券使用范围信息
     * 3.如果是指定用户，就给用户分配优惠券
     * @param request
     * @return
     */
    private Long saveCouponTemplate(AddOrUpdateCouponTemplateDto request,LiteCouponTemplate originModel){
        Date now  = new Date();
        LiteCouponTemplate model = LiteCouponTemplateConverter.addOrUpdateCouponTemplateDtoToModel(request);
        model.setLeftNum(model.getNum());
        //如果是指定用户，需要修改优惠券信息
        if(CollectionUtils.isNotEmpty(request.getUidAndPhoneList())){
            model.setLeftNum(0);
            model.setNum(request.getUidAndPhoneList().size());
            model.setUserLimitNum(1);
            model.setForNew(CouponTemplateConstant.FOR_NEW_F);
            model.setStatus(CouponTemplateConstant.STATUS_RUN_OUT);
        }
        //如果是针对大礼包的优惠券
        if(model.getType()== CouponTemplateTypeEnum.PACKAGE){
            model.setLeftNum(0);
            model.setNum(0);
            model.setUserLimitNum(0);
            model.setForNew(CouponTemplateConstant.FOR_NEW_F);
        }
        //如果request的ID不是空的，说明是编辑
        if(originModel!=null){
            Long bindId = originModel.getBindId()==null ? originModel.getId() : originModel.getBindId();
            model.setBindId(bindId);
            model.setChannelId(originModel.getChannelId());
        }
        model.setCreateTime(now);
        model.setUpdateTime(now);
        //保存优惠券信息
        liteCouponTemplateDao.save(model);
        if(model.getBindId()==null){
            model.setBindId(model.getId());
            liteCouponTemplateDao.updateById(model);
        }
        //保存优惠券使用范围信息
        liteCouponTemplateRangeService.addCouponTemplateRange(request,model.getId());
        //给指定的用户绑定优惠券
        if(CollectionUtils.isNotEmpty(request.getUidAndPhoneList())){
            for (UidAndPhone uidAndPhone : request.getUidAndPhoneList()) {
                liteCouponCenterService.bindUserCoupon(uidAndPhone.getUid(),uidAndPhone.getPhone(), UserCouponConstant.BIND_TYPE_ASSIGN,model,null,null);
            }
        }
        return model.getId();
    }

    @Override
    public Page<CouponTemplatePageListDto> queryCouponTemplatePage(CouponTemplateReqDto request) {
        Page<LiteCouponTemplate> page = liteCouponTemplateDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
            new QueryWrapper<LiteCouponTemplate>()
                    .eq("type",request.getType())
                    .eq("source_shop_id",request.getSourceShopId())
                    .eq(StringUtil.isNotEmpty(request.getStatus()),"status",request.getStatus())
                    .ne(StringUtil.isEmpty(request.getStatus()),"status",CouponTemplateConstant.STATUS_HISTORY)
                    .like(StringUtil.isNotEmpty(request.getTitle()),"title",request.getTitle())
                    .isNull(request.getHasAssign()!=null && !request.getHasAssign(),"package_id")
                    .isNotNull(request.getHasAssign()!=null && request.getHasAssign(),"package_id")
                    .isNotNull(request.getHasAssign()!=null && request.getHasAssign(),"package_id")
                    .orderByDesc("id"));

        List<LiteCouponTemplate> pageList = page.getRecords();
        if(CollectionUtils.isEmpty(pageList)){
            return new Page<CouponTemplatePageListDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(new ArrayList<>(0));
        }
        List<Long> bindIdList = pageList.stream().map(LiteCouponTemplate::getBindId).collect(Collectors.toList());
        Map<Long,List<CouponTemplateDto>> bindIdMap = LiteCouponTemplateConverter.modelList2DtoList(liteCouponTemplateDao.selectHistory(bindIdList))
                .stream().collect(Collectors.groupingBy(CouponTemplateDto::getBindId));

        List<CouponTemplatePageListDto> list = new ArrayList<>(pageList.size());
        for (LiteCouponTemplate couponTemplate : pageList) {
            CouponTemplatePageListDto dto = LiteCouponTemplateConverter.model2PageListDto(couponTemplate);
            dto.setHistory(bindIdMap.get(dto.getBindId()));
            list.add(dto);
        }
        return new Page<CouponTemplatePageListDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(list);
    }

    @Override
    public Page<CouponTemplatePageListDto> getAssignAbleTemplate(CouponTemplateReqDto request) {

        Page<LiteCouponTemplate> page = liteCouponTemplateDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<LiteCouponTemplate>()
                        .eq("type",CouponTemplateTypeEnum.PACKAGE.getCode())
                        .eq("status",CouponTemplateConstant.STATUS_VALID)
                        .eq("source_shop_id",request.getSourceShopId())
                        .eq("channel_id",request.getChannelId())
                        .like(StringUtil.isNotEmpty(request.getTitle()),"title",request.getTitle())
                        .isNull("package_id")
                        .isNull("delete_time")
                        .orderByDesc("id"));

        List<LiteCouponTemplate> pageList = page.getRecords();
        if(CollectionUtils.isEmpty(pageList)){
            return new Page<CouponTemplatePageListDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(new ArrayList<>(0));
        }
        return new Page<CouponTemplatePageListDto>(
                page.getCurrent(), page.getSize(),page.getTotal())
                .setRecords(LiteCouponTemplateConverter.modelList2PageDtoList(pageList));

    }

    @Override
    public Boolean addBindUrl(CouponAddBindUrlReqDto request) {
        LiteCouponTemplate model = liteCouponTemplateDao.getById(request.getId());
        if(model.getType().equals(CouponTemplateTypeEnum.ALIPAY)){
            PlatformChannelDto platFormChannel = platformChannelService.getPlatFormChannel(model.getChannelId());
            if(platFormChannel==null){
                throw new HzsxBizException("-1","未查询到渠道信息");
            }
            StringBuilder sb = new StringBuilder("alipays://platformapi/startapp?appId=2018122562686742&page=pages/index/index?originAppId=")
                    .append(platFormChannel.getAppId())
                    .append("&newUserTemplate=")
                    .append(request.getBindUrl());
            model.setBindUrl(sb.toString());
            liteCouponTemplateDao.updateById(model);
        }else {
            throw new HzsxBizException("-1","只有券码券才能设置领取链接");
        }
        return Boolean.TRUE;
    }

    @Override
    public String exportEntityNum(Long templateId) {
        LiteCouponTemplate couponTemplate = liteCouponTemplateDao.getById(templateId);
        if(couponTemplate.getType()!= CouponTemplateTypeEnum.ALIPAY){
            throw new HzsxBizException("-1","非券码券优惠券");
        }
        if(StringUtils.isNotEmpty(couponTemplate.getAliCodeFile())){
            return couponTemplate.getAliCodeFile();
        }
        String code = couponTemplate.getBindId()+"-"+ RandomUtil.randomString(8);


        Integer num = couponTemplate.getNum();
        List<CouponAlipayEntityNum> entityNumList = new ArrayList<>(num);
        while (num>0){
            CouponAlipayEntityNum couponAlipayEntityNum = new CouponAlipayEntityNum();
            couponAlipayEntityNum.setEntityNo(couponTemplate.getChannelId()+"-"+code+num);
            entityNumList.add(couponAlipayEntityNum);
            num--;
        }
        String fileName = SequenceTool.nextId() + ".csv";
        String savePath = OutsideConfig.TEMP_FILE_DIR+ File.separator+ fileName;
        EasyExcel.write(savePath,CouponAlipayEntityNum.class).sheet().doWrite(entityNumList);
        String fileUrl =  ossFileUtils.uploadExportFile("export",savePath,fileName);
        liteCouponTemplateDao.updateAliCodeFile(templateId,fileUrl);
        return fileUrl;
    }


}