package com.rent.common.dto.components.request;

import com.rent.common.dto.components.dto.RiskParamsDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Boan
 * Date: 2019/5/7
 * Desc:
 * Version:V1.0
 */
@Data
public class SxThreeElsRequest implements Serializable {

    private static final long serialVersionUID = 5790168116491101503L;

    /** 手机号 */
    private String phone;
    /** 身份证号 */
    private String idCardNo;
    /** 姓名 */
    private String userName;
    /** uid */
    private String uid;
    /** 订单编号 */
    private String orderId;
    /** 风控入参 */
    private RiskParamsDto riskParamsDto;

}
