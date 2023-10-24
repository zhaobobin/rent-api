    
package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserWordHistoryDao;
import com.rent.mapper.user.UserWordHistoryMapper;
import com.rent.model.user.UserWordHistory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * UserWordHistoryDao
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
@Repository
public class UserWordHistoryDaoImpl extends AbstractBaseDaoImpl<UserWordHistory, UserWordHistoryMapper> implements UserWordHistoryDao {


    @Override
    public List<UserWordHistory> getTenUserWordHistory(String uid) {
        return list(new QueryWrapper<UserWordHistory>()
                .select("word","id")
                .eq("uid",uid)
                .isNull("delete_time")
                .orderByDesc("create_time")
                .last("limit 10"));
    }

    @Override
    public Boolean deleteUserWordHistory(String uid) {
        UserWordHistory userWordHistory = new UserWordHistory();
        userWordHistory.setDeleteTime(new Date());
        update(userWordHistory,new QueryWrapper<UserWordHistory>().eq("uid",uid).isNull("delete_time"));
        return Boolean.TRUE;
    }

    @Override
    public UserWordHistory getByUidAndWord(String uid, String word) {
        return getOne(new QueryWrapper<UserWordHistory>().eq("uid",uid).eq("word",word).isNull("delete_time"));
    }
}
