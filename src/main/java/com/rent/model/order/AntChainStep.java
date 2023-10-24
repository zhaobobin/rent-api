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

import java.util.Date;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_ant_chain_step")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AntChainStep {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "shield_score")
    private String shieldScore;

    @TableField(value = "sync_to_chain")
    private Boolean syncToChain;

    @TableField(value = "insure")
    private Boolean insure;

    @TableField(value = "create_time")
    private Date createTime;

}