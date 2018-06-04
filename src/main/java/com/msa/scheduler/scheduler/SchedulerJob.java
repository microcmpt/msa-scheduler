package com.msa.scheduler.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * The type Scheduler job.
 *
 * @author sxp
 */
public class SchedulerJob implements Job {
    /**
     * Execute.
     *
     * @param context the context
     * @throws JobExecutionException the job execution exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
