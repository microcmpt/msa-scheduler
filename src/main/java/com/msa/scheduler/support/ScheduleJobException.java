package com.msa.scheduler.support;

/**
 * The type Schedule job exception.
 *
 * @author sxp
 */
public class ScheduleJobException extends RuntimeException {

    /**
     * Instantiates a new Schedule job exception.
     *
     * @param message the message
     */
    public ScheduleJobException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Schedule job exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ScheduleJobException(String message, Throwable cause) {
        super(message, cause);
    }
}
