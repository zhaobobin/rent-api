        
package com.rent.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.PageBackstageUserReq;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.backstage.resp.PageBackstageUserResp;
import com.rent.common.dto.user.BackstageUserDto;
import com.rent.common.dto.user.BackstageUserFunctionReqDto;
import com.rent.common.dto.user.BackstageUserReqDto;

import java.util.List;

/**
 * 后台用户Service
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
public interface BackstageUserService {

        /**
         * 新增后台用户
         * @param request 条件
         * @return boolean
         */
        Long addBackstageUser(BackstageUserDto request);

        /**
         * 根据 ID 修改后台用户
         * @param request 条件
         * @return String
         */
        Boolean modifyBackstageUser(BackstageUserDto request);

        /**
         * 删除用户
         * @param request
         * @return
         */
        Boolean deleteBackstageUser(BackstageUserReqDto request);

        /**
         * 根据条件查询一条记录
         * @param request 实体对象
         * @return BackstageUser
         */
        BackstageUserDto queryBackstageUserDetail(BackstageUserReqDto request);
        BackstageUserDto queryBackstageUserByPhone(String phone);

        /**
         * 根据条件列表
         * @param request 实体对象
         * @return BackstageUser
         */
        Page<PageBackstageUserResp> queryBackstageUserPage(PageBackstageUserReq request);


        /**
         * 权限设置页面
         * @param request
         * @return
         */
        List<AuthPageResp> authPage(BackstageUserReqDto request);

        /**
         * 更新用户权限
         * @param request
         * @return
         */
        Boolean updateBackstageUserAuth(BackstageUserFunctionReqDto request);

        /**
         * 店铺通过审核之后修改商家主账号的权限
         * @param shopId
         * @return
         */
        Boolean updateDepartmentAfterShopExaminePass(String shopId);
}