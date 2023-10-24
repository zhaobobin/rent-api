package com.rent.common.dto.product;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
public class ProductSkusImageResponse implements Serializable {
	private String name;
	private Integer id;

}
