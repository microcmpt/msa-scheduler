package com.msa.scheduler.web;

import com.google.common.collect.Maps;
import com.msa.scheduler.scheduler.ScheduleJobModule;

import java.util.List;
import java.util.Map;

/**
 * The type Base controller.
 */
public class BaseController {

    /**
     * Build success map.
     *
     * @return the map
     */
    protected Map<String, Object> buildSuccess() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "ok");
        return map;
    }

    /**
     * Build success map.
     *
     * @param jobs the jobs
     * @return the map
     */
    protected Map<String, Object> buildSuccess(List<ScheduleJobModule> jobs) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "ok");
        map.put("jobs", jobs);
        return map;
    }
}
