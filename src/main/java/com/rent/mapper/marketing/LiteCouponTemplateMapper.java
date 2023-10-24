        
package com.rent.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.marketing.LiteCouponTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * CouponTemplateDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponTemplateMapper extends BaseMapper<LiteCouponTemplate>{

    /**
     * 查询一条记录并锁定该数据
     * @param id
     * @return
     */
    @Select({"<script>",
            "SELECT * ",
            "FROM ct_lite_coupon_template ",
            "where bind_id = #{id} and delete_time is null for update",
            "</script>"}
    )
    LiteCouponTemplate selectByBindIdForUpdate(@Param("id") Long id);

    /**
     * 删除大礼包时，更新大礼包下的优惠券为未分配
     * @param id
     */
    @Update({"<script>",
            "update ct_lite_coupon_template set package_id=null",
            "where id = #{id}",
            "</script>"}
    )
    void updateUnassigned(Long id);
}