package com.rent.service.components;

import java.util.Map;

public interface SuningOpenApiNotifyService {
    void stageOrderCallback(Map<String, String> params);

    void yfbTradeRefundCallback(Map<String, String> params);
}
