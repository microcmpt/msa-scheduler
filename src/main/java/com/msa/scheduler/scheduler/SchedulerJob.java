package com.msa.scheduler.scheduler;

import com.msa.scheduler.http.OkHttpClientInvoker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * The type Scheduler job.
 *
 * @author sxp
 */
@Slf4j
public class SchedulerJob implements Job {

    /**
     * The Invoker.
     */
    private OkHttpClientInvoker invoker;

    /**
     * Instantiates a new Scheduler job.
     *
     * @param invoker the invoker
     */
    public SchedulerJob(OkHttpClientInvoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Execute.
     *
     * @param context the context
     * @throws JobExecutionException the job execution exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String url = (String) context.getMergedJobDataMap().get("request.url");
        invoker.invoke(url);
    }
}
