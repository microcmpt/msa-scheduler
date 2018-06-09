package com.msa.scheduler.web;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The type Global exception handler.
 * @author sxp
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler map.
     *
     * @param request  the request
     * @return the map
     */
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exceptionHandler(HttpServletRequest request, Throwable e) {
        Map<String, Object> respMap = Maps.newHashMap();
        respMap.put("status", "fail");
        respMap.put("message", e.getMessage());
        log.error("uri:{}, cause by:{}", request.getRequestURI(), e);
        return respMap;
    }
}
