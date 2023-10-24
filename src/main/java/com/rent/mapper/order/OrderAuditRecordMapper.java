        
package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.order.OrderAuditRecord;
import org.apache.ibatis.annotations.Param;

public interface OrderAuditRecordMapper extends BaseMapper<OrderAuditRecord>{

    /**
     * 获取分配审核单子最少的审核用户ID
     * @param type
     * @param shopId
     * @return
     */
    OrderAuditRecord getMinAssignedAuditUser(@Param("type")String type,@Param("shopId")String shopId);
}