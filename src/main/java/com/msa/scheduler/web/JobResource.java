package com.msa.scheduler.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The type Scheduler resource.
 */
@RestController
@RequestMapping("/api/v1")
public class JobResource {

    @PostMapping("/add/")
    public Map<String, Object> addJob() {
        return null;
    }
}
