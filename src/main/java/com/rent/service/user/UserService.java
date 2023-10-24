
package com.rent.service.user;

import com.rent.common.dto.user.UserStatisticsDto;

/**
 * 用户主体表Service
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
public interface UserService {

        /**
         * 首页用户数据统计
         * @return
         */
        UserStatisticsDto getUserStatistics();

}