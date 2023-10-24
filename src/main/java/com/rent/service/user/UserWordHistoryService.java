        
package com.rent.service.user;

import com.rent.common.dto.user.UserWordHistoryDto;

import java.util.List;

/**
 * 用户搜索记录Service
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
public interface UserWordHistoryService {

        /**
        * 新增用户搜索记录
        * @param request 条件
        * @return boolean
        */
        Long addUserWordHistory(UserWordHistoryDto request);


        /**
         * 获取最近10条用户的搜索记录
         * @param uid
         * @return
         */
        List<UserWordHistoryDto> getUserWordHistory(String uid);


        /**
         * 删除用户搜索记录
         * @param uid
         * @return
         */
        Boolean deleteUserWordHistory(String uid);
}