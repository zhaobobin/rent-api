package com.rent.common.dto.components.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ByteSignNotifyReq {

    private String appCode;
    private String contractCode;
    private String resultCode;
    private String transactionCode;
    private String resultDesc;
    private String downloadUrl;
    private String viewPdfUrl;

}
