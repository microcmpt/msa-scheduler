package com.msa.scheduler.web;

import com.google.common.collect.Maps;
import com.msa.scheduler.module.ScheduleJobModule;
import com.msa.scheduler.scheduler.CronJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The type Job controller.
 */
@RestController
public class CronJobController {
    @Autowired
    private CronJobService cronJobService;

    /**
     * Add job map.
     *
     * @return the map
     */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> addJob(@RequestBody ScheduleJobModule jobModule) {
        Map<String, Object> map = Maps.newHashMap();
        cronJobService.add(jobModule);
        map.put("status", "ok");
        return map;
    }

}