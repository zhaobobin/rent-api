package com.rent.common.dto.components.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AliPayFreezeResponse implements Serializable {

    private static final long serialVersionUID = 6614968680280010L;

    /** 预授权冻结url*/
    private String freezeUrl;
    /**支付流水号*/
    private String serialNo;

}
