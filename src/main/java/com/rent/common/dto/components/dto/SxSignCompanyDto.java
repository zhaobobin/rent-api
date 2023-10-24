package com.rent.common.dto.components.dto;

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
public class SxSignCompanyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String entQualificationType;

    private String entName;

    private String entQualificationNum;

    private String corporateName;

    private String personName;

    private String personIdCard;

    private String personMobile;

    private String keyWord;

    private String postSignCallback;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
