package com.msa.scheduler.module;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private boolean startNow;
}
