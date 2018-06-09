package com.msa.scheduler.web;

import com.msa.scheduler.scheduler.CronJobService;
import com.msa.scheduler.scheduler.ScheduleJobModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * The type Job controller.
 */
@RestController
@RequestMapping("/api/v1")
public class CronJobController extends BaseController {
    /**
     * The Cron job service.
     */
    @Autowired
    private CronJobService cronJobService;

    /**
     * Add job map.
     *
     * @param jobModule the job module
     * @return the map
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> addJob(@RequestBody @Valid ScheduleJobModule jobModule) {
        cronJobService.addJob(jobModule);
        return buildSuccess();
    }

    /**
     * Pause job map.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @return the map
     */
    @PutMapping(value = "/pause/job/{jobName}/group/{jobGroupName}")
    public Map<String, Object> pauseJob(@PathVariable("jobName") String jobName,
                                        @PathVariable("jobGroupName") String jobGroupName) {
        cronJobService.pauseJob(jobName, jobGroupName);
        return buildSuccess();
    }

    /**
     * Resume job map.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @return the map
     */
    @PutMapping(value = "/resume/job/{jobName}/group/{jobGroupName}")
    public Map<String, Object> resumeJob(@PathVariable("jobName") String jobName,
                                         @PathVariable("jobGroupName") String jobGroupName) {
        cronJobService.resumeJob(jobName, jobGroupName);
        return buildSuccess();
    }

    /**
     * Delete job map.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @return the map
     */
    @DeleteMapping(value = "/delete/job/{jobName}/group/{jobGroupName}")
    public Map<String, Object> deleteJob(@PathVariable("jobName") String jobName,
                                         @PathVariable("jobGroupName") String jobGroupName) {
        cronJobService.deleteJob(jobName, jobGroupName);
        return buildSuccess();
    }

    /**
     * Start job map.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @return the map
     */
    @PutMapping(value = "/start/job/{jobName}/group/{jobGroupName}")
    public Map<String, Object> startJob(@PathVariable("jobName") String jobName,
                                         @PathVariable("jobGroupName") String jobGroupName) {
        cronJobService.startNow(jobName, jobGroupName);
        return buildSuccess();
    }

    /**
     * Update job cron map.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @param cron         the cron
     * @return the map
     */
    @PutMapping(value = "/update/job/{jobName}/group/{jobGroupName}/cron{cron}")
    public Map<String, Object> updateJobCron(@PathVariable("jobName") String jobName,
                                        @PathVariable("jobGroupName") String jobGroupName,
                                        @PathVariable("jobGroupName") String cron) {
        cronJobService.updateJobCron(jobName, jobGroupName, cron);
        return buildSuccess();
    }

    @GetMapping("/hello")
    public String hello() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
        return "ok";
    }
}