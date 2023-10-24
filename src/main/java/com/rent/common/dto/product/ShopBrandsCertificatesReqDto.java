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
import java.util.Date;

/**
 * 店铺品牌证书表
 *
 * @author youruo
 * @Date 2020-06-17 10:38
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺品牌证书表")
public class ShopBrandsCertificatesReqDto extends Page implements Serializable {

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
     * 所属店铺品牌id
     * 
     */
    @Schema(description = "所属店铺品牌id")
    private Integer shopBrandId;

    /**
     * 证书图片链接
     * 
     */
    @Schema(description = "证书图片链接")
    private String src;

    /**
     * 类型 0为商标注册证，1为质检报告/3C认证 2为品牌授权书
     * 
     */
    @Schema(description = "类型 0为商标注册证，1为质检报告/3C认证 2为品牌授权书")
    private Boolean type;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
