
package com.rent.dao.user;


import com.rent.common.enums.user.EnumBackstageUserStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.AuditUser;

import java.util.List;


public interface AuditUserDao extends IBaseDao<AuditUser> {

    /**
     * 根据backstageUserId查询
     *
     * @param backstageUserId
     * @return
     */
    AuditUser getByBackstageUserId(Long backstageUserId);

    List<AuditUser> listShopAuditUser(String shopId, EnumBackstageUserStatus status);
}
