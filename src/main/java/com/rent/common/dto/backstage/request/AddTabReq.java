package com.rent.common.dto.backstage.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "tab下添加商品请求参数")
public class AddTabReq implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "排序值")
    private Integer indexSort;

    @Schema(description = "tab名称")
    private String name;

    @Schema(description = "跳转地址")
    private String jumpUrl;

    @TableField(value="渠道编号")
    private String channelId;

}
