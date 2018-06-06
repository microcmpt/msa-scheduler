package com.msa.scheduler.support;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * The type Target proxy url set event.
 */
@Deprecated
@Getter
public class TargetProxyUrlSetEvent extends ApplicationEvent {
    /**
     * The Target url.
     */
    private String targetUrl;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source    the object on which the event initially occurred (never {@code null})
     * @param targetUrl the target url
     */
    public TargetProxyUrlSetEvent(Object source, String targetUrl) {
        super(source);
        this.targetUrl = targetUrl;
    }
}
