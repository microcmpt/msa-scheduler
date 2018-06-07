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
            //——不触发立即执行
            //——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                    .cronSchedule(jobModule.getCron()).withMisfireHandlingInstructionDoNothing();
            //——以错过的第一个频率时间立刻开始执行
            //——重做错过的所有频率周期后
            //——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
            if (Objects.equals(jobModule.getMisfire(), Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY)) {
                 cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            //——以当前时间为触发频率立刻触发一次执行
            //——然后按照Cron频率依次执行
            } else if (Objects.equals(jobModule.getMisfire(), CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW)) {
                cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            }
            triggerBuilder.withSchedule(cronScheduleBuilder);
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("添加定时器任务异常");
        }
    }
}
