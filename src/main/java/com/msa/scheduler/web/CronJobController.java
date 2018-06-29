package com.msa.scheduler.web;

import com.google.common.collect.Lists;
import com.msa.scheduler.scheduler.CronJobService;
import com.msa.scheduler.scheduler.ScheduleJobModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        validateCronExpress(jobModule.getCron());
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
     * @param jobModule the job module
     * @return the map
     */
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> updateJobCron(@RequestBody ScheduleJobModule jobModule) {
        validateCronExpress(jobModule.getCron());
        cronJobService.updateJobCron(jobModule);
        return buildSuccess();
    }

    /**
     * Gets jobs.
     *
     * @param page the page
     * @return the jobs
     */
    @GetMapping(value = "/jobs/{page}")
    public Map<String, Object> getJobs(@PathVariable("page") int page) {
        List<ScheduleJobModule> jobs = cronJobService.getAllJobs();
        int pages = 1;
        if (!CollectionUtils.isEmpty(jobs)) {
            List<List<ScheduleJobModule>> jobPartitions = Lists.partition(jobs, 20);
            jobs = jobPartitions.get(page - 1);
            pages = jobPartitions.size();
        }
        return buildSuccess(jobs, page, pages);
    }

    /**
     * Load map.
     *
     * @param jobName      the job name
     * @param jobGroupName the job group name
     * @return the map
     */
    @GetMapping(value = "/load/job/{jobName}/group/{jobGroupName}")
    public Map<String, Object> load(@PathVariable("jobName") String jobName,
                                    @PathVariable("jobGroupName") String jobGroupName) {
        return buildSuccess(cronJobService.loadJob(jobName, jobGroupName));
    }
}