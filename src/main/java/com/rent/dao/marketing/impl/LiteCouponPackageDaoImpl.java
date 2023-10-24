package com.rent.dao.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.CouponPackageConstant;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.LiteCouponPackageDao;
import com.rent.mapper.marketing.LiteCouponPackageMapper;
import com.rent.model.marketing.LiteCouponPackage;
import com.rent.util.AppParamUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LiteCouponPackageDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Repository
public class LiteCouponPackageDaoImpl extends AbstractBaseDaoImpl<LiteCouponPackage, LiteCouponPackageMapper> implements LiteCouponPackageDao {


    @Override
    public List<LiteCouponPackage> getAvailableList(Boolean isNewUser) {
        List<LiteCouponPackage> packageList = list(new QueryWrapper<LiteCouponPackage>()
                .eq("channel_id", AppParamUtil.getChannelId())
                .eq("status", CouponPackageConstant.STATUS_VALID)
                .isNull("delete_time"));
        //如果不是新用户，需要将针对新用户的大礼包剔除掉
        if(!isNewUser){
            packageList = packageList.stream().filter(couponPackage -> !couponPackage.getForNew().equals(CouponPackageConstant.FOR_NEW_T)).collect(Collectors.toList());
        }
        return packageList;
    }

    @Override
    public LiteCouponPackage selectByBindIdForUpdate(Long id) {
        LiteCouponPackage couponPackage = baseMapper.selectByBindIdForUpdate(id);
        if(couponPackage==null){
            couponPackage = baseMapper.selectById(id);
            if(couponPackage.getDeleteTime()!=null){
                return null;
            }
        }
        return couponPackage;
    }

    @Override
    public void deleteById(Long id) {
        LiteCouponPackage couponPackage = new LiteCouponPackage().setId(id).setStatus(CouponPackageConstant.STATUS_INVALID).setDeleteTime(new Date());
        updateById(couponPackage);
    }

    @Override
    public void updateHistory(Long id) {
        LiteCouponPackage couponPackage = new LiteCouponPackage().setId(id).setStatus(CouponPackageConstant.STATUS_HISTORY).setDeleteTime(new Date());
        updateById(couponPackage);
    }
}
