package com.rent.common.dto.product;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 12132
 */
@Data
@Builder
@Accessors(chain = true)
public class SpecsResponse implements Serializable {
	private Integer id;
	private String name;
	private List<ProductSpecValueDto> values;
}
