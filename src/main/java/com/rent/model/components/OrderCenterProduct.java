package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 小程序订单中心商品信息
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_center_product")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderCenterProduct {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 产品ID
     */
    @TableField(value = "product_id")
    private String productId;
    /**
     * 文件在oss服务器中的名称
     */
    @TableField(value = "object_key")
    private String objectKey;
    /**
     * 文件在商品中心的素材标示，创建/更新商品时使用
     */
    @TableField(value = "material_key")
    private String materialKey;
    /**
     * 文件在商品中心的素材标识
     */
    @TableField(value = "material_id")
    private String materialId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
