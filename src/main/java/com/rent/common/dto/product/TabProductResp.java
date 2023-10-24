package com.rent.common.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TabProductResp implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    private Long id;

    private Integer indexSort;

    private String name;

    private Long tabId;

    private String image;

    private String price;

    private String oldNewDegree;

    private String itemId;

    private String linkUrl;

    private String shopName;


    private Integer salesVolume;

    private Integer status;

    private List<String> labels;
}
