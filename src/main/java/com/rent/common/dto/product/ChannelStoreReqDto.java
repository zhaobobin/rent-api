package com.rent.common.dto.product;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 渠道账号分佣
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "渠道账号分佣")
public class ChannelStoreReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "渠道编号")
    private String channelUID;

    @Schema(description = "渠道名称")
    private String channelName;

    @Schema(description = "门店名称")
    private String storeName;

    @Schema(description = "门店编码")
    private String storeCode;

    @Schema(description = "门店编码")
    private String storeAddress;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
