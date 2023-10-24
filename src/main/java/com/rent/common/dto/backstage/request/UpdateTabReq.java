package com.rent.common.dto.backstage.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "修改tab 请求参数")
public class UpdateTabReq implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "tabId")
    private Long id;

    @Schema(description = "排序值")
    private Integer indexSort;

    @Schema(description = "tab名称")
    private String name;

    @Schema(description = "跳转地址")
    private String jumpUrl;

    @TableField(value="渠道编号")
    private String channelId;

}
