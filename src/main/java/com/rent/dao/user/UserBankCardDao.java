package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.UserBankCard;

import java.util.List;

public interface UserBankCardDao extends IBaseDao<UserBankCard> {
    List<UserBankCard> getUserBankCardByUid(String uid);
}
