package com.rent.task;

import com.rent.service.order.AccountPeriodService;
import com.rent.service.order.ChannelAccountPeriodService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author zhaowenchao
 */
public class GenerateAccountPeriodJob extends QuartzJobBean {

    private AccountPeriodService accountPeriodService;
    private ChannelAccountPeriodService channelAccountPeriodService;

    public GenerateAccountPeriodJob(AccountPeriodService accountPeriodService, ChannelAccountPeriodService channelAccountPeriodService) {
        this.accountPeriodService = accountPeriodService;
        this.channelAccountPeriodService = channelAccountPeriodService;
    }


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        accountPeriodService.generateAccountPeriod();
        channelAccountPeriodService.generateAccountPeriod();
    }
}
