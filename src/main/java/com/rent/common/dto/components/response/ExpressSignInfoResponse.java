/**
 * Copyright 2020 bejson.com
 */
package com.rent.common.dto.components.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Auto-generated: 2020-07-02 14:49:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Schema(description = "物流签收信息查询响应")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressSignInfoResponse implements Serializable {

    private static final long serialVersionUID = 7958836322376696714L;
    @Schema(description = "签收结果")
    private Boolean result;
    @Schema(description = "签收时间")
    private Date signTime;
}
