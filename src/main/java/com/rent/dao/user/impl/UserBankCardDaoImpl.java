package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserBankCardDao;
import com.rent.mapper.user.UserBankCardMapper;
import com.rent.model.user.UserBankCard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserBankCardDaoImpl extends AbstractBaseDaoImpl<UserBankCard, UserBankCardMapper> implements UserBankCardDao {

    public List<UserBankCard> getUserBankCardByUid(String uid) {
        return list(new QueryWrapper<UserBankCard>().eq("uid", uid).isNull("delete_time"));
    }
}
