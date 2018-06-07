package com.msa.scheduler.web;

import com.google.common.collect.Maps;

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
}
