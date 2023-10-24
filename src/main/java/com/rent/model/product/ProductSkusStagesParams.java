
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品sku花呗分期表
 *
 * @author youruo
 * @Date 2020-08-31 16:57
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_skus_stages_params")
public class ProductSkusStagesParams {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
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
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 库存Id
     * 
     */
    @TableField(value="sku_id")
    private Long skuId;
    /**
     * 分期类型 -3期-3，6期-6，12期-12
     * 
     */
    @TableField(value="stage_type")
    private Integer stageType;
    /**
     * 是否包含手续费 不:包含，0:包含
     * 
     */
    @TableField(value="is_service_charge")
    private Integer isServiceCharge;
    /**
     * 手续费
     * 
     */
    @TableField(value="service_charge_price")
    private BigDecimal serviceChargePrice;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
