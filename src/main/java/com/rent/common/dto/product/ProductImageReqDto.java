package com.rent.common.dto.product;

import com.rent.common.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品主图表
 *
 * @author youruo
 * @Date 2020-06-16 15:17
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    private Long id;

    /**
     * CreateTime
     * 
     */
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    private Date deleteTime;

    /**
     * 图片链接
     * 
     */
    private String src;

    /**
     * 所属产品id
     * 
     */
    private String productId;

    /**
     * 商品展示主图  1 展示第一栏   其余设定排序按数字大小规则   不排序传0
     * 
     */
    private Integer isMain;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
