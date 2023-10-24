package com.rent.service.export;

import com.rent.common.dto.export.PrequalificationSheetDto;
import com.rent.common.dto.export.ReceiptConfirmationReceiptDto;

public interface FormWordService {

    /**
     * 预审单
     * @param orderId
     * @return
     */
    PrequalificationSheetDto prequalificationSheet(String orderId);

    /**
     * 回执单
     * @param orderId
     * @return
     */
    ReceiptConfirmationReceiptDto receiptConfirmationReceipt(String orderId);


}
