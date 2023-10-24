    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ShopSplitBillRuleConverter;
import com.rent.common.dto.product.ShopSplitBillRuleDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopSplitBillRuleDao;
import com.rent.mapper.product.ShopSplitBillRuleMapper;
import com.rent.model.product.ShopSplitBillRule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ShopSplitBillRuleDao
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Repository
public class ShopSplitBillRuleDaoImpl extends AbstractBaseDaoImpl<ShopSplitBillRule, ShopSplitBillRuleMapper> implements ShopSplitBillRuleDao{


    @Override
    public List<ShopSplitBillRuleDto> getDtoByAccountId(Long id) {
        List<ShopSplitBillRule> list = list(new QueryWrapper<ShopSplitBillRule>().eq("account_id",id));
        return ShopSplitBillRuleConverter.modelList2DtoList(list);
    }

    @Override
    public List<Long> getAccountIdsByType(String typeInfo) {
        List<ShopSplitBillRule> list = list(new QueryWrapper<ShopSplitBillRule>().select("account_id").eq("type",typeInfo));
        return list.stream().map(ShopSplitBillRule::getAccountId).collect(Collectors.toList());
    }

    @Override
    public String getTypeInfoByAccountId(Long accountId) {
        List<ShopSplitBillRule> list = list(new QueryWrapper<ShopSplitBillRule>().select("type").eq("account_id",accountId));
        return list.stream().map(ShopSplitBillRule::getType).collect(Collectors.joining(","));
    }

    @Override
    public Boolean deleteByAccountId(Long accountId) {
        return remove(new QueryWrapper<ShopSplitBillRule>().eq("account_id",accountId));
    }
}
