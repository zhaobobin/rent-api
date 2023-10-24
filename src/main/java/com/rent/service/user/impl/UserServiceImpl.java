
package com.rent.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.user.UserStatisticsDto;
import com.rent.dao.user.UserDao;
import com.rent.model.user.User;
import com.rent.service.user.UserService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户主体表Service
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserStatisticsDto getUserStatistics() {
        Date now = new Date();
        Date monthBegin = DateUtil.getBeginDayOfMonth(now);
        Date todayBegin = DateUtil.getDayBegin(now);
        Date yesterday = DateUtil.dateReduceDay(now,1);
        Date yesterdayBegin = DateUtil.getDayBegin(yesterday);
        Date yesterdayEnd = DateUtil.getDayEnd(yesterday);

        Integer todayIncrease = userDao.count(new QueryWrapper<User>().ge("create_time",todayBegin));
        Integer yesterdayIncrease = userDao.count(new QueryWrapper<User>().between("create_time",yesterdayBegin,yesterdayEnd));
        Integer monthIncrease = userDao.count(new QueryWrapper<User>().ge("create_time",monthBegin));

        UserStatisticsDto userStatisticsDto = new UserStatisticsDto();
        userStatisticsDto.setTodayIncrease(todayIncrease);
        userStatisticsDto.setYesterdayIncrease(yesterdayIncrease);
        userStatisticsDto.setMonthIncrease(monthIncrease);

        return userStatisticsDto;
    }


}