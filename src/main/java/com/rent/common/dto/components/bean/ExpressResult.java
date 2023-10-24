/**
 * Copyright 2020 bejson.com
 */
package com.rent.common.dto.components.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-07-02 14:49:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
@Schema(description = "查询结果")
public class ExpressResult {
    /** 快递公司 */
    @Schema(description = "快递公司")
    private String com;
    /** 快递单号 */
    @Schema(description = "快递单号")
    private String no;
    /** 快递公司 */
    @Schema(description = "快递公司")
    private String company;
    /** 运输详情 */
    @Schema(description = "运输详情")
    private List<NodeList> list;
    /** 1表示此快递单的物流信息不会发生变化，此时您可缓存下来；0表示有变化的可能性 */
    @Schema(description = "1表示此快递单的物流信息不会发生变化，此时您可缓存下来；0表示有变化的可能性")
    private String status;
    /**
     * 详细的状态信息，可能为null，仅作参考。其中：
     * PENDING 待查询
     * NO_RECORD 无记录
     * ERROR 查询异常
     * IN_TRANSIT 运输中
     * DELIVERING 派送中
     * SIGNED 已签收
     * REJECTED 拒签
     * PROBLEM 疑难件
     * INVALID 无效件
     * TIMEOUT 超时件
     * FAILED 派送失败
     * SEND_BACK 退回
     * TAKING 揽件
     */
    @Schema(description =
        "详细的状态信息，可能为null，仅作参考。其中：PENDING 待查询 NO_RECORD 无记录 ERROR 查询异常 IN_TRANSIT 运输中 DELIVERING 派送中 SIGNED 已签收 REJECTED 拒签\n"
            + " PROBLEM 疑难件 INVALID 无效件 TIMEOUT 超时件 FAILED 派送失败 SEND_BACK 退回 TAKING 揽件")
    private String status_detail;

}