package com.rent.common.dto.product;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
public class ProductSkusImageUionResponse implements Serializable {
	
	private static final long serialVersionUID = -7650005885819494350L;
	
	private String name;
	private Integer specId;
	private Integer specValueId;
	


}
