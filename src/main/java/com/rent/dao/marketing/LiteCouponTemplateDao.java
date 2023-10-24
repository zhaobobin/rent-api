        
package com.rent.dao.marketing;



import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.LiteCouponPackage;
import com.rent.model.marketing.LiteCouponTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CouponTemplateDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponTemplateDao extends IBaseDao<LiteCouponTemplate> {

    /**
     * 获取有效的优惠券
     * @param isNewUser
     * @param scene
     * @return
     */
    List<LiteCouponTemplate> getAvailableList(Boolean isNewUser,String scene);

    /**
     * 查询一条记录并锁定该数据,先根据ID查，查不出来再根据bindId查
     * @param id
     * @return
     */
    LiteCouponTemplate selectByBindIdForUpdate(Long id);

    /**
     * 查询一条记录并锁定该数据,先根据ID查，查不出来再根据bindId查
     * @param binds
     * @return
     */
    List<LiteCouponTemplate> selectHistory(List<Long> binds);

    /**
     * 根据ID列表获取优惠券信息
     * @param templateIds
     * @return
     */
    List<LiteCouponTemplate> getByIds(Set<Long> templateIds);


    /**
     * 根据ID删除优惠券
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据ID删除优惠券
     * @param id
     */
    void updateHistory(Long id);

    /**
     * 添加大礼包时，更新大礼包下的优惠券为已经分配大礼包
     * @param ids
     * @param liteCouponPackage
     */
    void updateAssigned(Set<Long> ids, LiteCouponPackage liteCouponPackage);

    /**
     * 删除大礼包时，更新大礼包下的优惠券为未分配
     * @param ids
     */
    void updateUnassigned(Set<Long> ids);

    /**
     * 根据BindId获取优惠券ID
     * @param templateId
     * @return
     */
    List<Long> getIdByBindId(Long templateId);

    /**
     * 优惠券模板添加 券码券 文件地址
     * @param templateId
     * @param fileUrl
     */
    void updateAliCodeFile(Long templateId, String fileUrl);

    /**
     * 获取大礼包对应的优惠券
     * @param packageIdList
     * @return
     */
    Map<Long, List<LiteCouponTemplate>> getByPackageIdList(List<Long> packageIdList);
}
