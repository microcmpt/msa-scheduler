package com.msa.scheduler.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * The type Scheduler job.
 *
 * @author sxp
 */
@Slf4j
@DisallowConcurrentExecution
public class SchedulerJob implements Job {

    /**
     * Execute.
     *
     * @param context the context
     * @throws JobExecutionException the job execution exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        String url = (String) context.getMergedJobDataMap().get("request.url");
//        invoker.invoke(url);
        log.info(">>>>>>>>>>{}execute", context.getJobDetail().getKey());

    }
}
