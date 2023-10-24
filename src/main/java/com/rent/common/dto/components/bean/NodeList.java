/**
 * Copyright 2020 bejson.com
 */
package com.rent.common.dto.components.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Auto-generated: 2020-07-02 14:49:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "物流详情")
public class NodeList {
    @Schema(description = "物流事件发生的时间")
    private Date datetime;
    @Schema(description = "快件当时所在区域，由于快递公司升级，现大多数快递不提供此信息")
    private String zone;
    @Schema(description = "物流事件的描述")
    private String remark;
}