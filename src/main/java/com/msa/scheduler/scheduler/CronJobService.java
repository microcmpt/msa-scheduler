package com.msa.scheduler.scheduler;

import com.msa.scheduler.module.ScheduleJobModule;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * The type Cron job service.
 */
@Service
public class CronJobService {

    /**
     * The Scheduler.
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * Add.
     *
     * @param jobModule the job module
     */
    public void add(ScheduleJobModule jobModule) {
        try {
            JobDetail var = scheduler.getJobDetail(new JobKey(jobModule.getJobName(), jobModule.getJobGroupName()));
            if (Objects.nonNull(var)) {
                throw new RuntimeException("Job已存在！");
            }
            JobDetail jobDetail= JobBuilder.newJob(SchedulerJob.class).withIdentity(jobModule.getJobName(),
                    jobModule.getJobGroupName()).build();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(jobModule.getTriggerName(), jobModule.getTriggerGroupName())
                    .withPriority(jobModule.getPriority());
            if (jobModule.isStartNow()) {
                triggerBuilder.startNow();
            }
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(jobModule.getCron()));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println(jobDetail.getKey().getGroup());
        } catch (SchedulerException e) {
            throw new RuntimeException("添加定时器任务异常");
        }
    }
}
