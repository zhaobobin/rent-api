package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺增值服务表
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺增值服务表")
public class ShopAdditionalServicesDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     * 
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除时间
     * 
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 店铺id
     * 
     */
    @Schema(description = "店铺id")
    private String shopId;

    /**
     * 增值服务名称
     * 
     */
    @Schema(description = "增值服务名称")
    private String name;

    /**
     * 增值服务内容
     * 
     */
    @Schema(description = "增值服务内容")
    @Size(max=800,message="增值服务内容字数超出，最多{max}个字")
    private String content;

    /**
     * 增值服务价格
     * 
     */
    @Schema(description = "增值服务价格")
    private BigDecimal price;

    /**
     * 增值服务审核状态；0:审核拒绝；1:审核中；2:审核成功；默认1
     * 
     */
    @Schema(description = "增值服务审核状态；0:审核拒绝；1:审核中；2:审核成功；默认1")
    private Integer approvalStatus;

    /**
     * 增值服务状态；0:无效；1:有效；默认1（店铺删除此服务，修改此状态为0）
     * 
     */
    @Schema(description = "增值服务状态；0:无效；1:有效；默认1（店铺删除此服务，修改此状态为0）")
    private Integer status;

    /**
     * 增值简短服务说明
     * 
     */
    @Schema(description = "增值简短服务说明")
    @Size(max=500,message="增值简短服务说明字数超出，最多{max}个字")
    private String description;


    /**
     * 店铺名称
     *
     */
    @Schema(description = "店铺名称")
    private String shopName;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
