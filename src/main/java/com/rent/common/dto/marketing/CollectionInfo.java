package com.rent.common.dto.marketing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionInfo implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;


    /**
     * 商品名称
     */
    private String resourceId;

    /**
     * 商品ID
     */
    private Integer num;

}
