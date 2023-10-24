
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 转单记录表
 * @author youruo
 * @Date 2021-12-22 17:55
 */
@Data
@Accessors(chain = true)
@TableName("ct_transfer_order_record")
public class TransferOrderRecord {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 商品Id-被转
     */
    @TableField(value = "transferred_product_id")
    private String transferredProductId;
    /**
     * 快照id-被转
     */
    @TableField(value = "transferred_snap_shot_id")
    private Long transferredSnapShotId;
    /**
     * 商品sku_id-被转
     */
    @TableField(value = "transferred_sku_id")
    private Long transferredSkuId;

    /**
     * 商品Id-接手
     */
    @TableField(value = "transfer_product_id")
    private String transferProductId;
    /**
     * 快照id-接手
     */
    @TableField(value = "transfer_snap_shot_id")
    private Long transferSnapShotId;
    /**
     * 商品sku_id-接手
     */
    @TableField(value = "transfer_sku_id")
    private Long transferSkuId;

    /**
     * 店铺id-被转
     */
    @TableField(value = "transferred_shop_id")
    private String transferredShopId;
    /**
     * 店铺id-接手
     */
    @TableField(value = "transfer_shop_id")
    private String transferShopId;
    /**
     * 操作员
     */
    @TableField(value = "operator")
    private String operator;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 删除时间
     */
    @TableField(value = "delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
