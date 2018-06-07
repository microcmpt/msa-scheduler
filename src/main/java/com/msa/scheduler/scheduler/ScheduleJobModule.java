package com.msa.scheduler.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.CronTrigger;
import org.quartz.Trigger;

/**
 * @author sxp
 */
@Getter
@Setter
@NoArgsConstructor
public class ScheduleJobModule {
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String cron;
    private int priority = Trigger.DEFAULT_PRIORITY;
    private int misfire = CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING;
    private String url;
    private String jobDescription;
    private String triggerDescription;
    private boolean startNow;
}
