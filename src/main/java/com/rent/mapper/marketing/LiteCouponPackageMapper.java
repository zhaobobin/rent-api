        
package com.rent.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.marketing.LiteCouponPackage;
import org.apache.ibatis.annotations.Select;

/**
 * LiteCouponPackageMapper
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponPackageMapper extends BaseMapper<LiteCouponPackage> {


    /**
     * 查询一条记录并锁定该数据
     * @param id
     * @return
     */
    @Select({"<script>",
            "SELECT id,name,num,template_ids,left_num,user_limit_num,for_new,delete_time",
            "FROM ct_lite_coupon_package ",
            "where bind_id = #{id} and delete_time is null for update",
            "</script>"}
    )
    LiteCouponPackage selectByBindIdForUpdate(Long id);
}