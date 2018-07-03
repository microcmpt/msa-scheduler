package com.msa.scheduler.scheduler;

import com.msa.scheduler.support.mail.NotifyEmailSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;

import java.util.Objects;

/**
 * The type Scheduler job listener.
 */
@Slf4j
public class SchedulerJobListener implements JobListener {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Mail sender.
     */
    private NotifyEmailSender mailSender;

    /**
     * Instantiates a new Scheduler job listener.
     *
     * @param name       the name
     * @param mailSender the mail sender
     */
    public SchedulerJobListener(String name, NotifyEmailSender mailSender) {
        this.name = name;
        this.mailSender = mailSender;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Job to be executed.
     *
     * @param context the context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

    }

    /**
     * Job execution vetoed.
     *
     * @param context the context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String content = mailTemplate(context, "vetoed", null);
        if (Objects.nonNull(mailSender)) {
            try {
                mailSender.sendMail(content);
            } catch (Exception e) {
                log.error("send mail error, cause by:", e);
            }
        }
        log.info(content);
    }

    /**
     * Job was executed.
     *
     * @param context      the context
     * @param jobException the job exception
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String content = mailTemplate(context, "wasExecuted", jobException);
        if (Objects.nonNull(mailSender)) {
            try {
                mailSender.sendMail(content);
            } catch (Exception e) {
                log.error("send mail error, cause by:", e);
            }
        }
        log.info(content);
    }

    /**
     * Mail template string.
     *
     * @param context the context
     * @param execute the execute
     * @return the string
     */
    private String mailTemplate(JobExecutionContext context, String execute, JobExecutionException e) {
        StringBuilder stringBuilder = new StringBuilder();
        JobDetail job = context.getJobDetail();
        JobKey jobKey = job.getKey();
        stringBuilder
                .append("Job【")
                .append(jobKey)
                .append(StringUtils.isEmpty(job.getDescription()) ? "" : ",")
                .append(job.getDescription())
                .append("】");
        if (Objects.equals("was vetoed", execute)) {
            stringBuilder.append("execute vetoed! Please check!");
        } else {
            stringBuilder.append("was execute!");
            if (Objects.nonNull(e)) {
                stringBuilder.append(" But execute exception, cause by:")
                        .append(e.getCause());
            }
        }
        return stringBuilder.toString();
    }
}
