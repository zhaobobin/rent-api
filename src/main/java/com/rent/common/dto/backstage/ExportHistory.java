package com.rent.common.dto.backstage;

import com.rent.common.enums.export.ExportStatus;
import lombok.Data;

import java.util.Date;

/**
 * @author zhaowenchao
 */
@Data
public class ExportHistory {

    /**
     * 导出文件名
     */
    private String fileName;

    /**
     * 导出时间
     */
    private Date exportTime;

    /**
     * 导出状态
     */
    private ExportStatus status;

    /**
     * 导出文件路径
     */
    private String  url;







}
