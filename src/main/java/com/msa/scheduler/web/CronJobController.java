package com.msa.scheduler.web;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The type Job controller.
 */
@RestController
@RequestMapping("/api/v1")
public class CronJobController {

    /**
     * Add job map.
     *
     * @return the map
     */
    @PostMapping("/add")
    public Map<String, Object> addJob() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "ok");
        return map;
    }
}
