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
    @NotBlank(message = "任务不能为空！")
    private String jobName;
    @NotBlank(message = "任务组不能为空！")
    private String jobGroupName;
    @NotBlank(message = "触发器不能为空！")
    private String triggerName;
    @NotBlank(message = "触发器组不能为空！")
    private String triggerGroupName;
    @NotBlank(message = "频率不能为空！")
    private String cron;
    private int priority = Trigger.DEFAULT_PRIORITY;
    private int misfire = CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING;
    private String applicationId = "";
    private String uri = "";
    private String url = "";
    private String jobDescription = "";
    private String triggerDescription = "";
    @Deprecated
    private boolean startNow;
    /**
     * 此字段只作为输出字段
     */
    private String state;
}
