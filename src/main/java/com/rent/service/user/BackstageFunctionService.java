        
package com.rent.service.user;

import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.enums.user.EnumBackstageUserPlatform;

import java.util.List;

/**
 * 后台功能点Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageFunctionService {
        /**
         * 获取功能点
         * @param platform
         * @param chosenFunction
         * @return
         */
        List<AuthPageResp> getFunctionList(EnumBackstageUserPlatform platform, List<Long> chosenFunction);
}