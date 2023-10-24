
package com.rent.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.AuditUserPageReq;
import com.rent.common.dto.backstage.resp.AuditUserPageResp;
import com.rent.model.user.AuditUser;

import java.util.List;


public interface AuditUserService {

    /**
     * 分页查看信审人员信息
     *
     * @param req
     * @return
     */
    Page<AuditUserPageResp> page(AuditUserPageReq req);


    /**
     * 切换信审人员状态
     *
     * @param id
     * @return
     */
    Boolean changeStatus(Long id);

    /**
     * 添加用户到信审人员
     *
     * @param backstageUserId
     * @return
     */
    Boolean add(Long backstageUserId, String qrcodeUrl);

    /**
     * 判断是否是信审人员
     *
     * @param backstageUserId
     * @return
     */
    Boolean isAuditUser(Long backstageUserId);

}