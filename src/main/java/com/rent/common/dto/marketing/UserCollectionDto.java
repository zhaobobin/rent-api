package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户收藏表
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户收藏表")
public class UserCollectionDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 用户ID
     * 
     */
    @Schema(description = "用户ID")
    private String uid;

    /**
     * 收藏的资源ID
     * 
     */
    @Schema(description = "收藏的资源ID")
    @NotNull(message = "收藏资源不能为空哦")
    private String resourceId;

    /**
     * 收藏的资源类型
     * 
     */
    @Schema(description = "收藏的资源类型")
    @NotNull(message = "收藏资源类型不能为空哦")
    private String resourceType;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
