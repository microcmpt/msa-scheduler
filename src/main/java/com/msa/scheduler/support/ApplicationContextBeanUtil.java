package com.msa.scheduler.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The type Application context bean util.
 */
@Component
public class ApplicationContextBeanUtil implements ApplicationContextAware{
    /**
     * The constant applicationContext.
     */
    private static ApplicationContext applicationContext;

    /**
     * Sets application context.
     *
     * @param applicationContext the application context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Gets bean.
     *
     * @param name the name
     * @return the bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
