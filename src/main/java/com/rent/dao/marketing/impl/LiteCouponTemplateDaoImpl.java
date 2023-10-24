package com.rent.dao.marketing.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.CouponPackageConstant;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.LiteCouponTemplateDao;
import com.rent.mapper.marketing.LiteCouponTemplateMapper;
import com.rent.model.marketing.LiteCouponPackage;
import com.rent.model.marketing.LiteCouponTemplate;
import com.rent.util.AppParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LiteCouponTemplateDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Repository
@Slf4j
public class LiteCouponTemplateDaoImpl extends AbstractBaseDaoImpl<LiteCouponTemplate, LiteCouponTemplateMapper> implements LiteCouponTemplateDao {


    @Override
    public List<LiteCouponTemplate> getAvailableList(Boolean isNewUser,String scene) {
        List<LiteCouponTemplate> couponTemplates = list(new QueryWrapper<LiteCouponTemplate>()
                .eq("channel_id", AppParamUtil.getChannelId())
                .eq("status", CouponTemplateConstant.STATUS_VALID)
                .isNull("delete_time"));

        List<LiteCouponTemplate> couponTemplateList = new ArrayList<>(couponTemplates.size());
        for (LiteCouponTemplate couponTemplate : couponTemplates) {
            if (couponTemplate.getType() != CouponTemplateTypeEnum.SINGLE && StringUtils.isEmpty(couponTemplate.getBindUrl())) {
                continue;
            }
            couponTemplateList.add(couponTemplate);
        }

        //如果不是新用户，需要将针对新用户的优惠券剔除掉
        if(!isNewUser){
            couponTemplateList = couponTemplateList.stream().filter(couponTemplate -> !couponTemplate.getForNew().equals(CouponPackageConstant.FOR_NEW_T)).collect(Collectors.toList());
        }
        //租赁券就要过滤掉买断券
        if(scene.equals(CouponTemplateConstant.SCENE_RENT)){
            couponTemplateList = couponTemplateList.stream().filter(couponTemplate -> !couponTemplate.getScene().equals(CouponPackageConstant.SCENE_BUY_OUT)).collect(Collectors.toList());
        }
        //买断券要过滤掉不首期券和租赁券
        if(scene.equals(CouponTemplateConstant.SCENE_BUY_OUT)){
            couponTemplateList = couponTemplateList.stream().filter(couponTemplate -> couponTemplate.getScene().equals(CouponPackageConstant.SCENE_BUY_OUT)).collect(Collectors.toList());
        }
        return couponTemplateList;
    }

    @Override
    public LiteCouponTemplate selectByBindIdForUpdate(Long id) {
        LiteCouponTemplate template = baseMapper.selectByBindIdForUpdate(id);
        if(template==null){
            template = baseMapper.selectById(id);
            if(template.getDeleteTime()!=null){
                return null;
            }
        }
        return template;
    }

    @Override
    public List<LiteCouponTemplate> selectHistory(List<Long> binds) {
        return list(new QueryWrapper<LiteCouponTemplate>().in("bind_id",binds).eq("status",CouponTemplateConstant.STATUS_HISTORY).orderByDesc("id"));
    }

    @Override
    public List<LiteCouponTemplate> getByIds(Set<Long> templateIds) {
        if(CollectionUtils.isEmpty(templateIds)){
            return new ArrayList<LiteCouponTemplate>();
        }
        return list(new QueryWrapper<LiteCouponTemplate>().in("id",templateIds).orderByDesc("discount_amount"));
    }

    @Override
    public void deleteById(Long id) {
        LiteCouponTemplate couponTemplate = new LiteCouponTemplate()
                .setId(id)
                .setStatus(CouponTemplateConstant.STATUS_INVALID)
                .setDeleteTime(new Date());
        updateById(couponTemplate);
    }

    @Override
    public void updateHistory(Long id) {
        LiteCouponTemplate couponTemplate = new LiteCouponTemplate()
                .setId(id)
                .setStatus(CouponTemplateConstant.STATUS_HISTORY)
                .setDeleteTime(new Date());
        updateById(couponTemplate);
    }

    @Override
    public void updateAssigned(Set<Long> ids, LiteCouponPackage couponPackage) {
        Date now = new Date();
        for (Long id : ids) {
            LiteCouponTemplate template = new LiteCouponTemplate();
            template.setId(id);
            template.setForNew(couponPackage.getForNew());
            template.setNum(couponPackage.getNum());
            template.setLeftNum(couponPackage.getNum());
            template.setNum(couponPackage.getNum());
            template.setPackageId(couponPackage.getId());
            template.setUpdateTime(now);
            updateById(template);
        }
    }

    @Override
    public void updateUnassigned(Set<Long> ids) {
        for (Long id : ids) {
            baseMapper.updateUnassigned(id);
        }
    }

    @Override
    public List<Long> getIdByBindId(Long templateId) {
        List<LiteCouponTemplate> couponTemplateList = list(new QueryWrapper<LiteCouponTemplate>()
                .select("id")
                .eq("bind_id", templateId));
        return couponTemplateList.stream().map(LiteCouponTemplate::getId).collect(Collectors.toList());
    }

    @Override
    public void updateAliCodeFile(Long templateId, String fileUrl) {
        LiteCouponTemplate couponTemplate = new LiteCouponTemplate().setId(templateId).setAliCodeFile(fileUrl);
        updateById(couponTemplate);
    }

    @Override
    public Map<Long, List<LiteCouponTemplate>> getByPackageIdList(List<Long> packageIdList) {
        List<LiteCouponTemplate> couponTemplateList = list(new QueryWrapper<LiteCouponTemplate>()
                .in("package_id", packageIdList));
        return couponTemplateList.stream().collect(Collectors.groupingBy(LiteCouponTemplate::getPackageId));
    }
}
