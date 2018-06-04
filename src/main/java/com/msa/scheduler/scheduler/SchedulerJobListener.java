package com.msa.scheduler.scheduler;

import com.msa.scheduler.mail.NotifyEmailSender;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * The type Scheduler job listener.
 */
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
        // TODO:任务被否决发送邮件通知对应的人员
    }

    /**
     * Job was executed.
     *
     * @param context      the context
     * @param jobException the job exception
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        // TODO:任务被执行后发送邮件通知对应的人员
    }
}
