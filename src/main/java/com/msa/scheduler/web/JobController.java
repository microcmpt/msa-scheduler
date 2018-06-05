package com.msa.scheduler.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The type Job controller.
 */
@RestController
@RequestMapping("/api/v1")
public class JobController {

    /**
     * Add job map.
     *
     * @return the map
     */
    @PostMapping("/add/")
    public Map<String, Object> addJob() {
        return null;
    }
}
