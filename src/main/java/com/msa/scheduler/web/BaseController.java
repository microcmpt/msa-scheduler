package com.msa.scheduler.web;

import com.google.common.collect.Maps;
import com.msa.scheduler.support.ScheduleJobException;
import org.quartz.CronExpression;

import java.text.ParseException;
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

    /**
     * Build success map.
     *
     * @param data        the data
     * @param currentPage the current page
     * @param pages       the pages
     * @return the map
     */
    protected Map<String, Object> buildSuccess(Object data, int currentPage, int pages) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "ok");
        map.put("message", "操作成功！");
        map.put("pages", pages);
        map.put("currentPage", currentPage);
        map.put("data", data);
        return map;
    }

    /**
     * Validate cron express.
     *
     * @param cron the cron
     */
    protected void validateCronExpress(String cron) {
        try {
            new CronExpression(cron);
        } catch (ParseException e) {
            throw new ScheduleJobException("频率表达式格式不正确，请检查！", e);
        }
    }
}
