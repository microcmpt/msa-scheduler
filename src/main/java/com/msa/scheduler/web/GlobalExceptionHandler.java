package com.msa.scheduler.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler map.
     *
     * @param request  the request
     * @param response the response
     * @return the map
     */
    @ExceptionHandler
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response) {
        //TODO:处理异常情况
    }
}
