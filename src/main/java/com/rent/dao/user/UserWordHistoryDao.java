        
package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.UserWordHistory;

import java.util.List;

/**
 * UserWordHistoryDao
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
public interface UserWordHistoryDao extends IBaseDao<UserWordHistory> {

    /**
     * 获取最近10条用户的搜索记录
     * @param uid
     * @return
     */
    List<UserWordHistory> getTenUserWordHistory(String uid);

    /**
     * 删除用户搜索历史
     * @param uid
     * @return
     */
    Boolean deleteUserWordHistory(String uid);

    /**
     * 检查用户搜索记录是否已经存在了
     * @param uid
     * @param word
     * @return
     */
    UserWordHistory getByUidAndWord(String uid, String word);
}
