package com.msa.scheduler.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler map.
     *
     * @param request  the request
     * @return the map
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request) {
        //TODO:处理异常情况
    }
}
