package com.rent.common.dto.product;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class SpecsDto implements Serializable {
	private Integer id;
	private String name;
	private Integer opeSpecId;
	private List<ProductSpecValueDto> values;


	
	
}
