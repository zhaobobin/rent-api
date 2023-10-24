package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumOrderAuditRefuseType;
import com.rent.common.enums.order.EnumOrderAuditStatus;
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
 * 订单审核记录表
 *
 * @author xiaoyao
 * @Date 2020-10-22 10:29
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单审核记录表")
public class OrderAuditRecordDto implements Serializable {

    private static final long serialVersionUID = -8701304326956146854L;


    /**
     * 主键
     * 
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 订单编号
     * 
     */
    @Schema(description = "订单编号")
    private String orderId;

    /**
     * 审核人uid
     * 
     */
    @Schema(description = "审核人uid")
    private String approveUid;

    /**
     * 审核人姓名
     * 
     */
    @Schema(description = "审核人姓名")
    private String approveUserName;

    /**
     * 审核时间
     * 
     */
    @Schema(description = "审核时间")
    private Date approveTime;

    /**
     * 审核结果 EnumOrderAuditStatus 00:待审核 01：通过 02：拒绝
     *
     */
    @Schema(description = "审核结果 EnumOrderAuditStatus 00:待审核 01：通过 02：拒绝")
    private EnumOrderAuditStatus approveStatus;

    /**
     * REFUSE01("01","小贷过多"),
     *     REFUSE02("02", "在途订单过多"),
     *     REFUSE03("03", "偿还能力"),
     *     REFUSE04("04", "欺诈风险"),
     *     REFUSE05("05", "法院涉案"),
     *     REFUSE06("06", "非本人租用"),
     *     REFUSE07("07", "他处还款逾期"),
     *     REFUSE08("08", "客户失联"),
     *     REFUSE09("09", "敏感行业"),
     *
     */
    @Schema(description = "REFUSE01(\"01\",\"小贷过多\"),\n" + "    REFUSE02(\"02\", \"在途订单过多\"),\n"
        + "    REFUSE03(\"03\", \"偿还能力\"),\n" + "    REFUSE04(\"04\", \"欺诈风险\"),\n"
        + "    REFUSE05(\"05\", \"法院涉案\"),\n" + "    REFUSE06(\"06\", \"非本人租用\"),\n"
        + "    REFUSE07(\"07\", \"他处还款逾期\"),\n" + "    REFUSE08(\"08\", \"客户失联\"),\n"
        + "    REFUSE09(\"09\", \"敏感行业\"),")
    private EnumOrderAuditRefuseType refuseType;

    /**
     * 审核备注
     * 
     */
    @Schema(description = "审核备注")
    private String remark;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     * 
     */
    @Schema(description = "更新时间")
    private Date updateTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
