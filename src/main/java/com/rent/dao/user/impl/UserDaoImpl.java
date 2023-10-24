
package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserDao;
import com.rent.mapper.user.UserMapper;
import com.rent.model.user.User;
import com.rent.util.AppParamUtil;
import com.rent.util.RandomUtil;
import com.rent.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDao
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
@Repository
public class UserDaoImpl extends AbstractBaseDaoImpl<User, UserMapper> implements UserDao {

    @Override
    public List<String> queryUidListByTelephone(String telephone) {
        return list(new QueryWrapper<User>().select("uid").eq("telephone", telephone)).stream().map(User::getUid).collect(Collectors.toList());
    }

    @Override
    public User getUserByUid(String uid) {
        return getOne(new QueryWrapper<User>().eq("uid", uid));
    }

    @Override
    public String addUser(String telephone) {
        String channelId = AppParamUtil.getChannelId();
        Date now = new Date();
        User user = new User();
        String randomStr = RandomUtil.randomString(12);
        String uid = StringUtil.generateUid() + randomStr;
        user.setCreateTime(now).setUpdateTime(now).setChannel(channelId).setUid(uid);
        save(user);
        return user.getUid();
    }

    @Override
    public void deleteById(String uid) {
        User user = new User();
        user.setDeleteTime(new Date());
        update(user, new QueryWrapper<User>().eq("uid", uid));
    }

    @Override
    public List<User> getByUidList(List<String> uidList) {
        return list(new QueryWrapper<User>().in("uid", uidList));
    }

    @Override

    public void updateUserStatusByUid(String uid, Boolean idCardStatus) {
        User user = new User();
        user.setIdCardPhotoStatus(idCardStatus);
        user.setUpdateTime(new Date());
        update(user, new UpdateWrapper<User>().eq("uid", uid));
    }

}
