package com.rent.service.offline;

import com.rent.common.dto.backstage.request.LoginReqDto;
import com.rent.common.dto.backstage.resp.LoginRespDto;

public interface OfflineUserLoginService {
    /**
     * 后台用户登录 获取用户信息
     * @param loginDto
     * @return
     */
    LoginRespDto login(LoginReqDto loginDto);
}
