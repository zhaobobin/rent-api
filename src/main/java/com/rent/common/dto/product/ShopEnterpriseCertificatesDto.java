package com.rent.common.dto.product;

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
 * 店铺资质证书表
 *
 * @author youruo
 * @Date 2020-06-17 10:45
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺资质证书表")
public class ShopEnterpriseCertificatesDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 关联shop_enterprice_info表中的id
     * 
     */
    @Schema(description = "关联shop_enterprice_info表中的id")
    private Integer seInfoId;

    /**
     * 证书图片链接
     * 
     */
    @Schema(description = "证书图片链接")
    private String image;

    /**
     * 类型 0为营业执照号 1为组织机构代码证 2为税务登记证 3为法人身份证正面 4为法人身份证背面
     * 
     */
    @Schema(description = "类型 0为营业执照号 1为组织机构代码证 2为税务登记证 3为法人身份证正面 4为法人身份证背面")
    private Integer type;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
