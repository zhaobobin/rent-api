package com.rent.common.dto.components.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Boan
 * Date: 2019/5/7
 * Desc:
 * Version:V1.0
 */
@Data
@ToString
public class DecisionRequest implements Serializable {

    private static final long serialVersionUID = 5501415550020938546L;

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
}
