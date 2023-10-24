
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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单合同
 *
 * @author zhao
 * @Date 2020-11-09 15:41
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_contract")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderContract {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /**
     * Id
     *
     */
    @TableField(value = "backstage_user_id")
    private Long backstageUserId;
    /**
     * 订单编号
     * 
     */
    @TableField(value="order_id")
    private String orderId;

    /**
     * 店铺ID
     *
     */
    @TableField(value="shop_id")
    private String shopId;

    /**
     * 签署过后的合同文件
     *
     */
    @TableField(value="origin_pdf")
    private String originPdf;

    /**
     * 签署过后的合同文件
     *
     */
    @TableField(value="signed_pdf")
    private String signedPdf;

    /**
     * 费用
     *
     */
    @TableField(value="fee")
    private BigDecimal fee;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;

    /**
     * 合同code,电子签章存证时需要的字段
     *
     */
    @TableField(value="contract_code")
    private String contractCode;

    /**
     * 签署人流水号,电子签章存证时需要的字段
     *
     */
    @TableField(value="signer_code")
    private String signerCode;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
