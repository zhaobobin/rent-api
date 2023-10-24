package com.rent.common.dto.marketing;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 新增或者编辑优惠券模板请求dto
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "优惠券模版")
public class CouponRangeReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对应的类型的值
     */
    @Schema(description = "对应的类型的值")
    private String value;

    /**
     * 对应的值的描述，比如商品名称|类型名称
     */
    @Schema(description = "对应的值的描述，比如商品名称|类型名称")
    private String description;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
