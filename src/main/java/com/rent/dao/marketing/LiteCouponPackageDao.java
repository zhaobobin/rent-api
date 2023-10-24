        
package com.rent.dao.marketing;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.LiteCouponPackage;

import java.util.List;

/**
 * CouponPackageDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponPackageDao extends IBaseDao<LiteCouponPackage> {

    /**
     * 获取可以领取的大礼包
     * @param isNewUser
     * @return
     */
    List<LiteCouponPackage> getAvailableList(Boolean isNewUser);

    /**
     * 查询一条记录并锁定该数据
     * @param id
     * @return
     */
    LiteCouponPackage selectByBindIdForUpdate(Long id);

    /**
     * 删除一个大礼包
     * @param id
     */
    void deleteById(Long id);


    /**
     * 删除一个大礼包
     * @param id
     */
    void updateHistory(Long id);
}
