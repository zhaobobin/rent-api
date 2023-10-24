package com.rent.common.dto.components.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:boannpx@163.com">Boan</a>
 * @version 1.0
 * @date 2019/8/20  11:36
 * @desc 通用msgrequest
 */
@Data
public class SendMsgRequest implements Serializable {

    private static final long serialVersionUID = -2321323058141951706L;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 变量map
     */
    private Map<String, Object> dataMap;

    /**
     * 模板id
     */
    private String templateId;


}
