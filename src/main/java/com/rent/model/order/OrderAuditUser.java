package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("ct_order_audit_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAuditUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "backstage_user_id")
    private Long backstageUserId;

    @TableField(value = "type")
    private EnumBackstageUserPlatform type;

    @TableField(value = "shop_id")
    private String shopId;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "qrcode_url")
    private String qrcodeUrl;

    @TableField(value = "order_id")
    private String orderId;
}
