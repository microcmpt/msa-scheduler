package com.msa.scheduler.scheduler;

import com.msa.scheduler.support.ApplicationContextBeanUtil;
import com.msa.scheduler.support.http.OkHttpClientInvoker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StopWatch;

import java.util.concurrent.ThreadLocalRandom;

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
        try {
            log.info("execute job:[{}] start...", context.getJobDetail().getKey());
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String url = (String) context.getJobDetail().getJobDataMap().get("url");
            if (url.contains(",")) {
                String[] var = url.split(",");
                url = var[ThreadLocalRandom.current().nextInt(var.length)];
            }
            OkHttpClientInvoker invoker = (OkHttpClientInvoker) ApplicationContextBeanUtil.getBean("okHttpClientInvoker");
            invoker.invoke(url);
            stopWatch.stop();
            log.info("execute job:[{}] end, take time {} ms.", context.getJobDetail().getKey(), stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            log.info("execute job:[{}] error, cause by:", context.getJobDetail().getKey(), e);
            throw new JobExecutionException(e);
        }
    }
}
