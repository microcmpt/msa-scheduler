package com.msa.scheduler.web;

import com.google.common.collect.Maps;
import com.msa.scheduler.support.ScheduleJobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * The type Global exception handler.
 *
 * @author sxp
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler map.
     *
     * @param request the request
     * @return the map
     */
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exceptionHandler(HttpServletRequest request, Throwable e) {
        Map<String, Object> respMap = Maps.newHashMap();
        respMap.put("status", "fail");

        if (e instanceof MethodArgumentNotValidException) {
            StringBuilder messageBuilder = new StringBuilder();
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
            fieldErrors.forEach(fieldError ->
                    messageBuilder.append(fieldError.getDefaultMessage()).append(",")
            );
            respMap.put("message", messageBuilder.substring(0, messageBuilder.length() - 1));
        } else if (e instanceof ScheduleJobException) {
            respMap.put("message", e.getMessage());
        } else {
            respMap.put("message", "system exception");
        }
        log.error("uri:{}, cause by:", request.getRequestURI(), e);
        return respMap;
    }
}
