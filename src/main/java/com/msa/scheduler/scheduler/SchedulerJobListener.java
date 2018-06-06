package com.msa.scheduler.scheduler;

import com.msa.scheduler.mail.NotifyEmailSender;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.Objects;

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
        if (Objects.nonNull(mailSender)) {
            mailSender.sendMail("");
        }
    }

    /**
     * Job was executed.
     *
     * @param context      the context
     * @param jobException the job exception
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (Objects.nonNull(mailSender)) {

        }
    }
}
