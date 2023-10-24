package com.rent.service.user;

import com.rent.common.dto.api.request.AddUserAddressReq;
import com.rent.common.dto.api.request.ModifyUserAddressReq;
import com.rent.common.dto.api.resp.ListUserAddressResp;
import com.rent.common.dto.user.UserAddressDto;

import java.util.List;

/**
 * 用户地址表Service
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
public interface UserAddressService {

    /**
     * 获取用户的地址信息
     *
     * @param uid
     * @return
     */
    List<ListUserAddressResp> getUserAddress(String uid);

    /**
     * 新增用户地址表
     *
     * @param request 条件
     * @return boolean
     */
    Long addUserAddress(AddUserAddressReq request);

    /**
     * 根据 ID 修改用户地址表
     *
     * @param request 条件
     * @return String
     */
    Boolean modifyUserAddress(ModifyUserAddressReq request);

    /**
     * 根据id查询地址信息
     * @param id 条件
     * @return 地址信息
     */
    UserAddressDto getUserAddressById(Long id);

    /**
     * 删除用户地址
     * @param id
     * @return
     */
    Boolean deleteUserAddress(Long id);
}