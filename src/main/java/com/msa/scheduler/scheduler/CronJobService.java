package com.msa.scheduler.scheduler;

import com.google.common.collect.Lists;
import com.msa.scheduler.support.ScheduleJobException;
import com.msa.scheduler.support.mail.NotifyEmailSender;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
     * The Sender.
     */
    @Autowired(required = false)
    private NotifyEmailSender sender;

    /**
     * Add job.
     *
     * @param jobModule the job module
     */
    @Transactional
    public void addJob(ScheduleJobModule jobModule) {
        try {
            boolean existsJob = scheduler.checkExists(new JobKey(jobModule.getJobName(), jobModule.getJobGroupName()));
            if (existsJob) {
                throw new ScheduleJobException("Job已存在！");
            }

            JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class).withIdentity(jobModule.getJobName(),
                    jobModule.getJobGroupName()).withDescription(jobModule.getJobDescription()).build();
            jobDetail.getJobDataMap().put("url", jobModule.getUrl());
            jobDetail.getJobDataMap().put("applicationId", jobModule.getApplicationId());
            jobDetail.getJobDataMap().put("path", jobModule.getPath());
            // 添加Job监听器
            Matcher matcher = KeyMatcher.keyEquals(jobDetail.getKey());
            scheduler.getListenerManager().addJobListener(new SchedulerJobListener(jobDetail.getKey() + "Listener", sender), matcher);

            Trigger va1 = scheduler.getTrigger(new TriggerKey(jobModule.getTriggerName(), jobModule.getTriggerGroupName()));
            if (Objects.nonNull(va1)) {
                throw new ScheduleJobException("Trigger已存在！");
            }

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(jobModule.getTriggerName(), jobModule.getTriggerGroupName())
                    .withPriority(jobModule.getPriority());
            if (jobModule.isStartNow()) {
                triggerBuilder.startNow();
            }
            // 不触发立即执行，等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                    .cronSchedule(jobModule.getCron()).withMisfireHandlingInstructionDoNothing();
            // 以错过的第一个频率时间立刻开始执行，重做错过的所有频率周期后，当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
            if (Objects.equals(jobModule.getMisfire(), Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY)) {
                cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            // 以当前时间为触发频率立刻触发一次执行，然后按照Cron频率依次执行
            } else if (Objects.equals(jobModule.getMisfire(), CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW)) {
                cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            }
            triggerBuilder.withSchedule(cronScheduleBuilder).withDescription(jobModule.getTriggerDescription());
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new ScheduleJobException("添加定时器任务异常！", e);
        }
    }

    /**
     * Pause job.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     */
    public void pauseJob(String jobName, String jobGroupName) {
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new ScheduleJobException("暂停定时任务异常！", e);
        }
    }

    /**
     * Resume job.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     */
    public void resumeJob(String jobName, String jobGroupName) {
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new ScheduleJobException("恢复定时任务异常！", e);
        }
    }

    /**
     * Delete job.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     */
    public void deleteJob(String jobName, String jobGroupName) {
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new ScheduleJobException("删除定时任务异常！", e);
        }
    }

    /**
     * Start now.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     */
    public void startNow(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new ScheduleJobException("启动定时任务异常！", e);
        }
    }

    /**
     * Update job cron.
     *
     * @param job the job
     */
    public void updateJobCron(ScheduleJobModule job) {
        deleteJob(job.getJobName(), job.getJobGroupName());
        addJob(job);
    }

    /**
     * Gets all jobs.
     *
     * @return the all jobs
     */
    public List<ScheduleJobModule> getAllJobs() {
        List<ScheduleJobModule> jobs = Lists.newArrayList();
        try {
            List<String> groups = scheduler.getJobGroupNames();
            groups.forEach(group -> {
                try {
                    scheduler.getJobKeys(GroupMatcher.groupEquals(group)).forEach(jobKey -> {
                        ScheduleJobModule job = new ScheduleJobModule();
                        String jobName = jobKey.getName();
                        String jobGroup = jobKey.getGroup();
                        job.setJobName(jobName);
                        job.setJobGroupName(jobGroup);
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            JobDataMap jobDataMap = jobDetail.getJobDataMap();
                            job.setApplicationId(jobDataMap.getString("applicationId"));
                            job.setPath(jobDataMap.getString("path"));
                            job.setUrl(jobDataMap.getString("url"));
                            job.setJobDescription(jobDetail.getDescription());
                            CronTrigger cronTrigger = (CronTrigger) scheduler.getTriggersOfJob(jobKey).get(0);
                            job.setCron(cronTrigger.getCronExpression());
                            TriggerKey triggerKey = cronTrigger.getKey();
                            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
                            job.setTriggerName(triggerKey.getName());
                            job.setTriggerGroupName(triggerKey.getGroup());
                            job.setState(triggerState.name());
                        } catch (SchedulerException e) {
                            throw new ScheduleJobException("获取所有定时任务异常！", e);
                        }
                        jobs.add(job);
                    });
                } catch (SchedulerException e) {
                    throw new ScheduleJobException("获取所有定时任务异常！", e);
                }
            });
        } catch (SchedulerException e) {
            throw new ScheduleJobException("获取所有定时任务异常！", e);
        }

        return jobs;
    }

    /**
     * Load job schedule job module.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @return the schedule job module
     */
    public ScheduleJobModule loadJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            ScheduleJobModule module = new ScheduleJobModule();
            module.setJobName(jobName);
            module.setJobGroupName(jobGroupName);
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            module.setApplicationId(jobDataMap.getString("applicationId"));
            module.setPath(jobDataMap.getString("path"));
            module.setUrl(jobDataMap.getString("url"));
            module.setJobDescription(jobDetail.getDescription());

            CronTrigger cronTrigger = (CronTrigger) scheduler.getTriggersOfJob(jobKey).get(0);
            module.setPriority(cronTrigger.getPriority());
            module.setCron(cronTrigger.getCronExpression());
            module.setMisfire(cronTrigger.getMisfireInstruction());
            module.setTriggerDescription(cronTrigger.getDescription());
            module.setTriggerName(cronTrigger.getKey().getName());
            module.setTriggerGroupName(cronTrigger.getKey().getGroup());
            return module;
        } catch (SchedulerException e) {
            throw new ScheduleJobException("获取job详情异常！", e);
        }
    }
}
