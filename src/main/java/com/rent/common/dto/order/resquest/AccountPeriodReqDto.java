package com.rent.common.dto.order.resquest;


import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumAccountPeriodStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 账期表
 *
 * @author xiaotong
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "账期表")
public class AccountPeriodReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private String shopId;
    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;
    /**
     * 状态：待结算；待审核；待支付；已支付
     */
    @Schema(description = "状态")
    private EnumAccountPeriodStatus status;
    /**
     * 结算日期
     */
    @Schema(description = "结算日期")
    private Date settleDate;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
