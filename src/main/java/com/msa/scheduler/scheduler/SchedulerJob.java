package com.msa.scheduler.scheduler;

import com.msa.api.regcovery.discovery.ServiceDiscovery;
import com.msa.scheduler.support.ApplicationContextBeanUtil;
import com.msa.scheduler.support.ScheduleJobException;
import com.msa.scheduler.support.http.OkHttpClientInvoker;
import com.msa.scheduler.support.http.OkHttpClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.Objects;
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
        OkHttpClientInvoker invoker = (OkHttpClientInvoker) ApplicationContextBeanUtil.getBean("okHttpClientInvoker");
        OkHttpClientProperties okHttpClientProperties = (OkHttpClientProperties) ApplicationContextBeanUtil.getBean("okHttpClientProperties");
        String url = "";
        try {
            log.info("execute job:[{}] start...", context.getJobDetail().getKey());
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

            String applicationId = context.getJobDetail().getJobDataMap().getString("applicationId");
            ServiceDiscovery serviceDiscovery = null;
            try {
                serviceDiscovery = (ServiceDiscovery) ApplicationContextBeanUtil.getBean("serviceDiscovery");
            } catch (NoSuchBeanDefinitionException ex) {
            }
            try {
                if (Objects.nonNull(serviceDiscovery)) {
                    url = serviceDiscovery.discover(jobDataMap.getString("applicationId")).replace(":=", "");
                    String uri =  jobDataMap.getString("uri");
                    url = uri.startsWith("/") ? "http://" + url + uri : "http://" + uri + "/" + uri;
                }
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
            invoker.invoke(url);
            stopWatch.stop();
            log.info("execute job:[{}] end, take time {} ms.", context.getJobDetail().getKey(), stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            int retries = okHttpClientProperties.getRetries();
            boolean retrySucc = false;
            if (retries != 0) {
                int var = 0;
                while (var < retries) {
                    retrySucc = retryRequest(url, invoker);
                    if (retrySucc) {
                        break;
                    }
                    var ++;
                }
            }
            if (!retrySucc) {
                log.info("execute job:[{}] error, cause by:", context.getJobDetail().getKey(), e);
                throw new JobExecutionException(e);
            }
        }
    }

    /**
     * Retry request boolean.
     *
     * @param url     the url
     * @param invoker the invoker
     * @return the boolean
     */
    private boolean retryRequest(String url, OkHttpClientInvoker invoker) {
        boolean hasNoException = true;
        try {
            invoker.invoke(url);
        } catch (Exception e) {
            hasNoException = false;
        }
        return hasNoException;
    }
}
