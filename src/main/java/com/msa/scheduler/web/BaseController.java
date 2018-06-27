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
        map.put("message", "操作成功！");
        return map;
    }

    /**
     * Build success map.
     *
     * @param data the data
     * @return the map
     */
    protected Map<String, Object> buildSuccess(Object data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "ok");
        map.put("message", "操作成功！");
        map.put("data", data);
        return map;
    }
}
