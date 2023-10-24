package com.rent.common.dto.components.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Boan
 * Date: 2019/5/7
 * Desc:
 * Version:V1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentCreditResponse implements Serializable {
    private static final long serialVersionUID = -1406178745609404633L;

    /** 是否关单 */
    private Boolean close;
    /** 结果 */
    private Boolean reslut;
    /** 授信额度 */
    private BigDecimal rentCreditAmt;
    /** 评级 */
    private String orderLevel;
    /** 押金系数 */
    private BigDecimal riskCoefficient;

}
