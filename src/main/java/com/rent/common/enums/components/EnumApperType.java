package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * 上报类型
 */
@Getter
@AllArgsConstructor
public enum EnumApperType {
    /**
     * 描述
     */
    APPLY_FOR_SUBMISSION("00", "申请报送"),
    LOAN_SUBMISSION("01", "放款报送"),
    REPAYMENT_SUBMISSION("02", "还款报送"),
    COMPENSATION_SUBMISSION("03", "代偿报送"),
    RECOVER_SUBMISSION("04", "追偿报送"),
    OVER_DUE_REPAYMENT_SUBMISSION("05", "逾期还款报送"),
    SETTLE_CREDIT("06", "结清"),
    SETTLE_CREDIT_REPAY("07", "追偿结清"),

    ;

    /**
     * 状态码
     */
    @EnumValue
    @JsonValue
    private String code;

    /**
     * 状态描述
     */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumApperType } 实例
     **/
    public static EnumApperType find(String code) {
        for (EnumApperType instance : EnumApperType.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }


    /**
     * 交租计划
     */
    public static final Set<EnumApperType> RENT_PAYMENT_PLAN_SET = Sets.newHashSet(EnumApperType.REPAYMENT_SUBMISSION, EnumApperType.COMPENSATION_SUBMISSION, EnumApperType.RECOVER_SUBMISSION);


    /**
     * 正常还款
     */
    public static final Set<EnumApperType> NORMAL_REPAYMENT_SET = Sets.newHashSet(EnumApperType.REPAYMENT_SUBMISSION, EnumApperType.RECOVER_SUBMISSION);


    /**
     * 正常申请报送
     */
    public static final Set<EnumApperType> NORMAL_APPLY_SET = Sets.newHashSet(EnumApperType.APPLY_FOR_SUBMISSION, EnumApperType.LOAN_SUBMISSION);


    /**
     * 代偿报送需要的状态
     */
    public static final Set<EnumApperType> NORMAL_COMPENSATION_SET = Sets.newHashSet(EnumApperType.COMPENSATION_SUBMISSION, EnumApperType.RECOVER_SUBMISSION);

}