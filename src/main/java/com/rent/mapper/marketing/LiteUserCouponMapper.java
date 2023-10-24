        
package com.rent.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.marketing.LiteUserCoupon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * UserCouponDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteUserCouponMapper extends BaseMapper<LiteUserCoupon>{

    /**
     * 更新优惠券为未使用
     * @param code
     * @return
     */
    @Update({"<script>",
            "update ct_lite_user_coupon set status=\"UNUSED\",order_id=null,use_time=null where code = #{code}",
            "</script>"}
    )
    void updateCouponUnused(@Param("code") String code);

    /**
     * 替换用户优惠券表的中uid
     * @param origin 原来的uid
     * @param newUid 替换成新的uid
     * @return
     */
    @Update({"<script>",
            "update ct_lite_user_coupon set uid=#{newUid} where uid = #{origin}",
            "</script>"}
    )
    Boolean replaceUid(@Param("origin")String origin,@Param("newUid") String newUid);
}