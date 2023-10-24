package com.rent.common.dto.marketing;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotSearchSaveReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "小程序渠道编号")
    private String channelId;

    @Schema(description = "热门搜索关键词列表")
    private List<String> words;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
