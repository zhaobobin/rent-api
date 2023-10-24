package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户订单列表查询入参")
public class OrderListQueryRequest implements Serializable {

    /** 用户uid */
    @NotBlank
    private String uid;
    /** 订单状态列表 */
    private List<String> statusList;
    /** 是否查询逾期标志 */
    private Boolean overDueQueryFlag ;

    private Integer pageNumber;

    private Integer pageSize;

    private List<String> channelIdList;

    @Override
    public String toString() {
        return "OrderListRequestModel{" + "uid='" + uid + '\'' + ", statusList=" + statusList + ", pageNumber="
            + pageNumber + ", pageSize=" + pageSize + '}';
    }
}
