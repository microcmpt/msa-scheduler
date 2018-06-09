package com.msa.scheduler.scheduler;

import com.msa.api.regcovery.discovery.ServiceDiscovery;
import com.msa.scheduler.support.ApplicationContextBeanUtil;
import com.msa.scheduler.support.ScheduleJobException;
import com.msa.scheduler.support.http.OkHttpClientInvoker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

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
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            String url = "";
            String applicationId = context.getJobDetail().getJobDataMap().getString("applicationId");
            ServiceDiscovery serviceDiscovery = (ServiceDiscovery) ApplicationContextBeanUtil.getBean("serviceDiscovery");
            try {
                url = serviceDiscovery.discover(jobDataMap.getString("applicationId")).replace(":=", "");
                String uri =  jobDataMap.getString("uri");
                url = uri.startsWith("/") ? "http://" + url + uri : "http://" + uri + "/" + uri;
            } catch (Exception e) {
                log.warn("{} can not found in service registry!", applicationId);
            }
            if (!StringUtils.hasText(url)) {
                url = jobDataMap.getString("url");
                if (!StringUtils.hasText(url)) {
                    throw new ScheduleJobException("url is empty");
                }
                if (url.contains(",")) {
                    String[] var = url.split(",");
                    url = var[ThreadLocalRandom.current().nextInt(var.length)];
                }
                if (!url.startsWith("http://")) {
                    url = "http://" + url;
                }
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
