package com.rent.config.quartz;

import com.rent.task.*;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhaowenchao
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail orderOverdueJob() {
        JobDetail jobDetail = JobBuilder.newJob(OrderOverDueJob.class)
                .withIdentity("orderOverdueJob", "orderOverdueJobGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger orderOverdueTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(orderOverdueJob())
                .withIdentity("orderOverdueTrigger", "orderOverdueTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 22 * * ? *"))
                .build();
        return trigger;
    }


    @Bean
    public JobDetail billMaturityJob() {
        JobDetail jobDetail = JobBuilder.newJob(BillMaturityJob.class)
                .withIdentity("billMaturityJob", "billMaturityJobGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger billMaturityTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(billMaturityJob())
                .withIdentity("billMaturityTrigger", "billMaturityTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ? *"))
                .build();
        return trigger;
    }


    @Bean
    public JobDetail stageOrderPayJob() {
        JobDetail jobDetail = JobBuilder.newJob(StageOrderPayJob.class)
                .withIdentity("stageOrderPayJob", "stageOrderPayJobGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger stageOrderPayTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(stageOrderPayJob())
                .withIdentity("stageOrderPayTrigger", "stageOrderPayTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ? *"))
                .build();
        return trigger;
    }


    @Bean
    public JobDetail generateSplitBillJob() {
        JobDetail jobDetail = JobBuilder.newJob(GenerateSplitBillJob.class)
                .withIdentity("generateSplitBillJob", "generateSplitBillGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger generateSplitBillTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(generateSplitBillJob())
                .withIdentity("generateSplitBillTrigger", "generateSplitBillTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ? *"))
                .build();
        return trigger;
    }

    @Bean
    public JobDetail generateAccountPeriodJob() {
        JobDetail jobDetail = JobBuilder.newJob(GenerateAccountPeriodJob.class)
                .withIdentity("generateAccountPeriodJob", "generateAccountPeriodGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger generateAccountPeriodTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(generateAccountPeriodJob())
                .withIdentity("generateAccountPeriodTrigger", "generateAccountPeriodTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ? *"))
                .build();
        return trigger;
    }

    @Bean
    public JobDetail confirmOrderReceiptJob() {
        JobDetail jobDetail = JobBuilder.newJob(ConfirmOrderReceiptJob.class)
                .withIdentity("confirmOrderReceiptJob", "confirmOrderReceiptGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger confirmOrderReceiptTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(confirmOrderReceiptJob())
                .withIdentity("confirmOrderReceiptTrigger", "confirmOrderReceiptTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 17,23 * * ? *"))
                .build();
        return trigger;
    }

    @Bean
    public JobDetail orderStatisticsTaskJob() {
        JobDetail jobDetail = JobBuilder.newJob(OrderStatisticsJob.class)
                .withIdentity("orderStatisticsTaskJob", "orderStatisticsTaskGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger orderStatisticsTaskTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(orderStatisticsTaskJob())
                .withIdentity("orderStatisticsTaskTrigger", "orderStatisticsTaskTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 1 * * ? *"))
                .build();
        return trigger;
    }


    @Bean
    public JobDetail initProductScoreTaskJob() {
        JobDetail jobDetail = JobBuilder.newJob(InitProductSortScoreJob.class)
                .withIdentity("initProductScoreTaskJob", "initProductScoreTaskGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger initProductScoreTaskTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(initProductScoreTaskJob())
                .withIdentity("initProductScoreTaskTrigger", "initProductScoreTaskTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 1 * * ? *"))
                .build();
        return trigger;
    }

    @Bean
    public JobDetail swipingOrderJob() {
        JobDetail jobDetail = JobBuilder.newJob(SwipingOrderJob.class)
                .withIdentity("swipingOrderJob", "swipingOrderJobGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger swipingOrderTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger().forJob(swipingOrderJob())
                .withIdentity("swipingOrderTrigger", "swipingOrderTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 1 * * ? *"))
                .build();
        return trigger;
    }
}
