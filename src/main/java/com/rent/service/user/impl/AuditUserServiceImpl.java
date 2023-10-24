
package com.rent.service.user.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.AuditUserPageReq;
import com.rent.common.dto.backstage.resp.AuditUserPageResp;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import com.rent.dao.user.AuditUserDao;
import com.rent.dao.user.BackstageUserDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.AuditUser;
import com.rent.model.user.BackstageUser;
import com.rent.service.user.AuditUserService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuditUserServiceImpl implements AuditUserService {

    private final AuditUserDao auditUserDao;
    private final BackstageUserDao backstageUserDao;

    @Override
    public Page<AuditUserPageResp> page(AuditUserPageReq req) {
        Page<AuditUser> page = auditUserDao.page(
                new Page<>(req.getPageNumber(), req.getPageSize()),
                new QueryWrapper<AuditUser>()
                        .like(StringUtils.isNotEmpty(req.getName()), "name", req.getName())
                        .eq("shop_id", LoginUserUtil.getLoginUser().getShopId())
        );

        List<AuditUserPageResp> dtos = new ArrayList<>(page.getRecords().size());
        for (AuditUser auditUser : page.getRecords()) {
            AuditUserPageResp resp = new AuditUserPageResp();
            resp.setId(auditUser.getId());
            resp.setBackstageUserId(auditUser.getBackstageUserId());
            resp.setName(auditUser.getName());
            resp.setMobile(auditUser.getMobile());
            resp.setStatus(auditUser.getStatus());
            resp.setQrcodeUrl(auditUser.getQrcodeUrl());
            dtos.add(resp);
        }
        return new Page<AuditUserPageResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(dtos);
    }

    @Override
    public Boolean changeStatus(Long id) {
        AuditUser auditUser = auditUserDao.getById(id);
        EnumBackstageUserStatus enumBackstageUserStatus = EnumBackstageUserStatus.INVALID.equals(auditUser.getStatus()) ? EnumBackstageUserStatus.VALID : EnumBackstageUserStatus.INVALID;
        auditUser.setStatus(enumBackstageUserStatus);
        auditUserDao.updateById(auditUser);
        return Boolean.TRUE;
    }

    @Override
    public Boolean add(Long backstageUserId, String qrcodeUrl) {
        AuditUser auditUser = auditUserDao.getByBackstageUserId(backstageUserId);
        if (auditUser != null) {
            throw new HzsxBizException("-1", "该用户已经是信审人员！");
        }
        BackstageUser backstageUser = backstageUserDao.getById(backstageUserId);
        auditUser = new AuditUser();
        Date now = new Date();
        auditUser.setCreateTime(now);
        auditUser.setUpdateTime(now);
        auditUser.setBackstageUserId(backstageUserId);
        auditUser.setMobile(backstageUser.getMobile());
        auditUser.setName(backstageUser.getName());
        auditUser.setStatus(EnumBackstageUserStatus.VALID);
        auditUser.setType(backstageUser.getType());
        auditUser.setShopId(backstageUser.getShopId());
        auditUser.setQrcodeUrl(qrcodeUrl);
        auditUserDao.save(auditUser);
        return Boolean.TRUE;
    }

    @Override
    public Boolean isAuditUser(Long backstageUserId) {
        AuditUser auditUser = auditUserDao.getByBackstageUserId(backstageUserId);
        if (auditUser == null) {
            return Boolean.FALSE;
        }
        return auditUser.getStatus().equals(EnumBackstageUserStatus.VALID);
    }
}