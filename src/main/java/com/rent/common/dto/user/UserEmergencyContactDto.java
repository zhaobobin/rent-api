package com.rent.common.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户紧急联系人")
public class UserEmergencyContactDto implements Serializable {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "关系")
    private String relationship;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "填写时间")
    private Date createTime;


    @Schema(description = "是否认证通过")
    private Boolean checked;


}
