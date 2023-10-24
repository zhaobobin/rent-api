package com.rent.common.dto.marketing;

import com.rent.common.dto.product.ListProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 栏目配置
 *
 * @author xiaotong
 * @Date 2020-12-21 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "栏目配置")
public class ColumnConfigListDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 渠道编号
     *
     */
    @Schema(description = "渠道编号")
    private String channelId;

    /**
     * 背景图片
     * 
     */
    @Schema(description = "背景图片")
    private String url;

    /**
     * 栏目名称
     *
     */
    @Schema(description = "栏目名称")
    private String name;

    /**
     * 商品id
     *
     */
    @Schema(description = "商品id")
    private List<String> productIds;

    /**
     * 文案
     *
     */
    @Schema(description = "文案")
    private String paperwork;

    /**
     * 商品
     *
     */
    @Schema(description = "商品")
    private List<ListProductDto> products;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
