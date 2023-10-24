package com.rent.common.dto.backstage;

import com.rent.common.dto.user.BackstageFunctionChooseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 后台部门
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentFunctionChooseReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long departmentId;

    private List<BackstageFunctionChooseDto> backstageFunctionChooseDtoList;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
