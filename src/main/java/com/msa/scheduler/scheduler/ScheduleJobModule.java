package com.msa.scheduler.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.quartz.CronTrigger;
import org.quartz.Trigger;

/**
 * @author sxp
 */
@Getter
@Setter
@NoArgsConstructor
public class ScheduleJobModule {
    @NotBlank(message = "jobName is required")
    private String jobName;
    @NotBlank(message = "jobGroupName is required")
    private String jobGroupName;
    @NotBlank(message = "triggerName is required")
    private String triggerName;
    @NotBlank(message = "triggerGroupName is required")
    private String triggerGroupName;
    @NotBlank(message = "cron is required")
    private String cron;
    private int priority = Trigger.DEFAULT_PRIORITY;
    private int misfire = CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING;
    private String applicationId;
    private String uri;
    private String url;
    private String jobDescription;
    private String triggerDescription;
    private boolean startNow;
}
