package com.rent.task;

import com.rent.service.order.UserOrdersTaskService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @author zhaowenchao
 */

public class InitProductSortScoreJob extends QuartzJobBean {

    private UserOrdersTaskService userOrdersTaskService;

    public InitProductSortScoreJob(UserOrdersTaskService userOrdersTaskService) {
        this.userOrdersTaskService = userOrdersTaskService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        userOrdersTaskService.initProductScore();
    }
}
