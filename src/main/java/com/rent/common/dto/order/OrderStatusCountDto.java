package com.rent.common.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusCountDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer waitPay = 0;

    private Integer waitDelivery = 0;;

    private Integer waitReceive = 0;;

    private Integer  renting = 0;;

    private Integer waitSettle = 0;;

    private Integer overdue = 0;;


    public void incrWaitPay(){
        this.waitPay ++;
    }

    public void incrWaitDelivery(){
        this.waitDelivery ++;
    }

    public void incrWaitReceive(){
        this.waitReceive ++;
    }

    public void incrRenting(){
        this.renting ++;
    }

    public void incrWaitSettle(){
        this.waitSettle ++;
    }

    public void incrOverdue(){
        this.overdue ++;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
