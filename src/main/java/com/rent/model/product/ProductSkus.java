
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
 * 商品sku表
 *
 * @author youruo
 * @Date 2020-06-16 15:27
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_skus")
public class ProductSkus {

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
     * 产品ID
     * 
     */
    @TableField(value="item_id")
    private String itemId;
    /**
     * 市场价
     * 
     */
    @TableField(value="market_price")
    private BigDecimal marketPrice;
    /**
     * 当前库存
     * 
     */
    @TableField(value="inventory")
    private Integer inventory;
    /**
     * 总库存
     * 
     */
    @TableField(value="total_inventory")
    private Integer totalInventory;
    /**
     * 1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新
     * 
     */
    @TableField(value="old_new_degree")
    private Integer oldNewDegree;
    /**
     * 是否可以买断 1:可以买断。0:不可以买断
     * 
     */
    @TableField(value="buy_out_support")
    private Integer buyOutSupport;
    /**
     * 销售价
     * 
     */
    @TableField(value="sale_price")
    private BigDecimal salePrice;
    /**
     * 押金
     *
     */
    @TableField(value="deposit_price")
    private BigDecimal depositPrice;

    @TableField(value="is_support_stage")
    private Integer isSupportStage;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
