package com.rent.common.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户搜索记录
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWordHistoryDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    private Long id;

    /**
     * CreateTime
     * 
     */
    private Date createTime;

    /**
     * 用户搜索关键字
     * 
     */
    private String word;

    /**
     * 用户ID
     * 
     */
    private String uid;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
