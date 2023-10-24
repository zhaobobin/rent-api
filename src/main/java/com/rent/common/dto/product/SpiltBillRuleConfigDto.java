package com.rent.common.dto.product;

import com.rent.common.enums.product.EnumSplitBillAppVersion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
public class SpiltBillRuleConfigDto {

    @Schema(description = "商家支付宝账号")
    private String identity;

    @Schema(description = "商家支付宝实名认证姓名")
    private String name;



    /**
     * ruleId
     */
    @Schema(description = "ruleId")
    private Long ruleId;

    @Schema(description = "分账类型 买断|租金")
    private String type;
    /**
     * 延迟天数
     *
     */
    @Schema(description = "延迟天数")
    private Integer delayNum;

    /**
     * 延迟天数类型
     *
     */
    @Schema(description = "延迟天数类型")
    private String delayType;

    /**
     * 分账比例，小于等于1
     *
     */
    @Schema(description = "分账比例，小于等于1")
    private BigDecimal scale;

    /**
     * 创建时间
     *
     */
    @Schema(description = "创建时间")
    private Date createTime;


    @Schema(description = "分账规则所属小程序版本-LITE:简版 ZWZ:租物租")
    private EnumSplitBillAppVersion appVersion;

    @Schema(description = "周期")
    private List<String> cycle;

}
