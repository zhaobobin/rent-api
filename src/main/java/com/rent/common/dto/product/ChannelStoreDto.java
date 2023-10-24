package com.rent.common.dto.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rent.model.product.ChannelStoreAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 渠道下的营销码汇总表
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "渠道下的门店信息")
public class ChannelStoreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "首页链接")
    private String link;

    @Schema(description = "门店编码")
    private String marketingId;

    @Schema(description = "渠道ID")
    private String channelSplitId;

    @Schema(description = "渠道名称")
    private String channelName;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "删除时间")
    private Date deleteTime;

    @Schema(description = "已推订单")
    private Integer orderNum;

    @Schema(description = "成功订单")
    private Integer successNum;

    @Schema(description = "在租订单")
    private Integer rentingNum;

    @Schema(description = "失效保护天数")
    private Integer expireDay;

    @Schema(description = "用户绑定")
    private Integer expireSwitch;

    @Schema(description = "门店名称")
    private String shopName;

    @Schema(description = "失效保护天数")
    private String telephone;

    @Schema(description = "失效保护天数")
    private String realName;

    @Schema(description = "失效保护天数")
    private String uid;

    @Schema(description = "门店地址")
    private ChannelStoreAddress address;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
