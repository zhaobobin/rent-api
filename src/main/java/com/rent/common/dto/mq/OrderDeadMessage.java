package com.rent.common.dto.mq;

import com.rent.common.enums.mq.EnumOrderDeadOperate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhaowenchao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeadMessage implements Serializable {

    private String orderId;

    private EnumOrderDeadOperate operate;




}
