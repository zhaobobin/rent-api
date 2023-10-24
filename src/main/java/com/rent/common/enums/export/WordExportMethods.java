package com.rent.common.enums.export;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum WordExportMethods {

    PREQUALIFICATION_SHEET("template/PrequalificationSheet.docx", "预审单"),
    RECEIPT_CONFIRMATION_RECEIPT("template/ReceiptConfirmationReceipt.docx", "回执单"),
    ;

    @EnumValue
    @JsonValue
    private String code;

    private String description;


    public static WordExportMethods find(String code) {
        for (WordExportMethods instance : WordExportMethods.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
