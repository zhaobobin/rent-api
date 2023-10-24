package com.rent.model.order;

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
 * 商家风控关单
 *
 * @author xiaoyao
 * @Date 2020-06-17 16:54
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_shop_close")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderShopClose {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * ShopId
     */
    @TableField(value = "shop_id")
    private String shopId;
    /**
     * ShopOperatorId
     */
    @TableField(value = "shop_operator_id")
    private String shopOperatorId;
    /**
     * OrderId
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 关闭原因
     */
    @TableField(value = "close_reason")
    private String closeReason;
    /**
     * CertificateImages
     */
    @TableField(value = "certificate_images")
    private String certificateImages;
    /**
     * CreateTime
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * DeleteTime
     */
    @TableField(value = "delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
