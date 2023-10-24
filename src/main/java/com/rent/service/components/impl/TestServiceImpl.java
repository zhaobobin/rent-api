package com.rent.service.components.impl;

import com.rent.service.components.TestService;
import com.rent.service.order.AccountPeriodService;
import com.rent.service.order.ChannelAccountPeriodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final AccountPeriodService accountPeriodService;
    private final ChannelAccountPeriodService channelAccountPeriodService;

    @Override
    public void test() {
        accountPeriodService.generateAccountPeriod();
        channelAccountPeriodService.generateAccountPeriod();
    }
}
