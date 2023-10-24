        
package com.rent.dao.product;

import com.rent.common.dto.product.ShopSplitBillRuleDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopSplitBillRule;

import java.util.List;

/**
 * ShopSplitBillRuleDao
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
public interface ShopSplitBillRuleDao extends IBaseDao<ShopSplitBillRule> {


    /**
     * 根据分账账户ID获取到分账规则列表
     * @param id
     * @return
     */
    List<ShopSplitBillRuleDto> getDtoByAccountId(Long id);

    /**
     * 获取某个分账类型的分账账户id列表
     * @param typeInfo
     * @return
     */
    List<Long> getAccountIdsByType(String typeInfo);

    /**
     * 获取一个分账账户下面的分账类型，多个用，隔开
     * @param accountId
     * @return
     */
    String getTypeInfoByAccountId(Long accountId);

    /**
     * 根据AccountId删除记录
     * @param accountId
     * @return
     */
    Boolean deleteByAccountId(Long accountId);
}
