    
package com.rent.dao.product.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.product.EnumSplitBillAccountStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopSplitBillAccountDao;
import com.rent.mapper.product.ShopSplitBillAccountMapper;
import com.rent.model.product.ShopSplitBillAccount;
import com.rent.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ShopSplitBillAccountDao
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Repository
public class ShopSplitBillAccountDaoImpl extends AbstractBaseDaoImpl<ShopSplitBillAccount, ShopSplitBillAccountMapper> implements ShopSplitBillAccountDao{


    @Override
    public void init(String shopId, String shopName, String shopFirmInfo) {
        Date now = new Date();
        List<ShopSplitBillAccount> list = getByShopId(shopId);
        if(CollectionUtil.isEmpty(list)){
            //如果是空的，说明是第一次
            ShopSplitBillAccount shopSplitBillAccount = new ShopSplitBillAccount();
            shopSplitBillAccount.setShopId(shopId);
            shopSplitBillAccount.setShopName(shopName);
            shopSplitBillAccount.setShopFirmInfo(shopFirmInfo);
            shopSplitBillAccount.setCreateTime(now);
            shopSplitBillAccount.setStatus(EnumSplitBillAccountStatus.UNSET);
            save(shopSplitBillAccount);
        }else{
            //如果不是空的，就更新店铺审核信息
            for (ShopSplitBillAccount shopSplitBillAccount : list) {
                shopSplitBillAccount.setShopId(shopId);
                shopSplitBillAccount.setShopName(shopName);
                shopSplitBillAccount.setShopFirmInfo(shopFirmInfo);
                shopSplitBillAccount.setUpdateTime(now);
            }
            updateBatchById(list,list.size());
        }
    }

    @Override
    public List<ShopSplitBillAccount> getByShopId(String shopId){
        return list(new QueryWrapper<ShopSplitBillAccount>().eq("shop_id",shopId));
    }

    @Override
    public List<ShopSplitBillAccount> getByPassedShopId(String shopId) {
        return list(new QueryWrapper<ShopSplitBillAccount>()
                .eq("shop_id",shopId)
                .eq("status",EnumSplitBillAccountStatus.PASS)
                .orderByDesc("audit_time"));
    }

    @Override
    public List<ShopSplitBillAccount> getShopList(String shopName) {
        List<ShopSplitBillAccount> list = list(new QueryWrapper<ShopSplitBillAccount>()
                .select("shop_id","shop_name","shop_firm_info")
                .like(StringUtil.isNotEmpty(shopName),"shop_name",shopName));
        return list.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void saveAccountInfo(ShopSplitBillAccount accountModel) {
        ShopSplitBillAccount account = getOne(new QueryWrapper<ShopSplitBillAccount>().select("id").eq("shop_id",accountModel.getShopId()).eq("status",EnumSplitBillAccountStatus.UNSET));
        if(account==null){
            //如果没有未设置的分账信息，就新增一条记录
            save(accountModel);
        }else{
            //如果有未设置的分账信息，就更新未设置的那条信息
            accountModel.setId(account.getId());
            updateById(accountModel);
        }
    }

}
