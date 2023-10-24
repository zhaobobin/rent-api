        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.constant.CouponPackageConstant;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.dto.marketing.CouponPackageDto;
import com.rent.model.marketing.LiteCouponPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 优惠券大礼包Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public class LiteCouponPackageConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static CouponPackageDto model2Dto(LiteCouponPackage model) {
        if (model == null) {
            return null;
        }
        CouponPackageDto dto = new CouponPackageDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTemplateIds(model.getTemplateIds());
        dto.setForNew(model.getForNew());
        dto.setStatus(model.getStatus());
        dto.setLeftNum(model.getLeftNum());
        dto.setNum(model.getNum());
        dto.setUserLimitNum(model.getUserLimitNum());
        dto.setCreateTime(model.getCreateTime());
        dto.setType(model.getType());
        dto.setChannelId(model.getChannelId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static LiteCouponPackage dto2Model(CouponPackageDto dto) {
        if (dto == null) {
            return null;
        }
        LiteCouponPackage model = new LiteCouponPackage();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setTemplateIds(dto.getTemplateIds());
        model.setForNew(dto.getForNew());
        model.setStatus(dto.getStatus());
        model.setLeftNum(dto.getLeftNum());
        model.setNum(dto.getNum());
        model.setUserLimitNum(dto.getUserLimitNum());
        model.setCreateTime(dto.getCreateTime());
        model.setType(dto.getType());
        model.setSourceShopId(dto.getSourceShopId());
        model.setSourceShopName(dto.getSourceShopName());
        model.setChannelId(dto.getChannelId());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<CouponPackageDto> modelList2DtoList(List<LiteCouponPackage> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), LiteCouponPackageConverter::model2Dto));
    }

    /**
     *
     * @param sceneSet
     * @return
     */
    public static String getContainScene(Set<String> sceneSet) {
        int a = 0;
        if (sceneSet.contains(CouponTemplateConstant.SCENE_BUY_OUT)){
            a = a|1;
        }
        if(sceneSet.contains(CouponTemplateConstant.SCENE_FIRST)){
            a = a|2;
        }
        if(sceneSet.contains(CouponTemplateConstant.SCENE_RENT)){
            a = a|2;
        }

        switch (a){
            case 1:return CouponPackageConstant.SCENE_BUY_OUT;
            case 2:return CouponPackageConstant.SCENE_RENT;
            case 3:return CouponPackageConstant.SCENE_BOTH;
            default:return null;
        }
    }
}