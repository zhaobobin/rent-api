
package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.User;

import java.util.List;

/**
 * UserDao
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
public interface UserDao extends IBaseDao<User> {


    /**
     * 根据手机号码获取用户ID列表
     *
     * @param telephone
     * @return
     */
    List<String> queryUidListByTelephone(String telephone);

    /**
     * 根据用户ID获取用户信息
     *
     * @param uid
     * @return
     */
    User getUserByUid(String uid);

    /**
     * 创建一个新的用户并且返回uid
     *
     * @param telephone
     * @return
     */
    String addUser(String telephone);

    /**
     * 删除一名用户
     *
     * @param uid
     */
    void deleteById(String uid);

    /**
     * 根据uid修改用户身份证认证状态
     *
     * @param uid
     * @return
     */
    void updateUserStatusByUid(String uid, Boolean idCardStatus);


    /**
     * 批量查询用户信息
     *
     * @param uidList
     * @return
     */
    List<User> getByUidList(List<String> uidList);
}
