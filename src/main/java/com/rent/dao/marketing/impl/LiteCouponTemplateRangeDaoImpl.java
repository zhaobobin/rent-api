package com.rent.dao.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.marketing.CouponRangeDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.LiteCouponTemplateRangeDao;
import com.rent.mapper.marketing.LiteCouponTemplateRangeMapper;
import com.rent.model.marketing.LiteCouponTemplateRange;
import com.rent.util.AppParamUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LiteCouponTemplateRangeDao
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Repository
public class LiteCouponTemplateRangeDaoImpl extends AbstractBaseDaoImpl<LiteCouponTemplateRange, LiteCouponTemplateRangeMapper> implements LiteCouponTemplateRangeDao {


    @Override
    public List<LiteCouponTemplateRange> getInRangeTemplateId(String type, String value) {
        List<LiteCouponTemplateRange> list =  list(new QueryWrapper<LiteCouponTemplateRange>().select("template_id","type")
                .eq("type",type)
                .eq("value",value)
                .eq("channel_id", AppParamUtil.getChannelId())
        );
        return  list;
    }

    @Override
    public CouponRangeDto getCouponRange(Long templateId) {
        List<LiteCouponTemplateRange> list =  list(new QueryWrapper<LiteCouponTemplateRange>().select("value","type").eq("template_id",templateId));
        StringBuilder stringBuilder = new StringBuilder();
        list.stream().forEach(couponTemplateRange -> stringBuilder.append(couponTemplateRange.getValue()).append(","));

        CouponRangeDto dto = new CouponRangeDto();
        dto.setType(list.get(0).getType());
        dto.setValue(stringBuilder.substring(0,stringBuilder.length()-1));
        return dto;
    }

    @Override
    public String getCouponRangeType(Long templateId) {
        List<LiteCouponTemplateRange> list =  list(new QueryWrapper<LiteCouponTemplateRange>()
                .select("type")
                .eq("template_id",templateId)
                .last("limit 1"));
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0).getType();
    }

    @Override
    public List<LiteCouponTemplateRange> getCouponTemplateRangeByTemplateId(Long templateId) {
        return list(new QueryWrapper<LiteCouponTemplateRange>().eq("template_id",templateId));
    }

}
