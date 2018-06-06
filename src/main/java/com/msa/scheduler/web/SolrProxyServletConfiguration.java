package com.msa.scheduler.web;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The type Solr proxy servlet configuration.
 * @author sxp
 */
@Deprecated
//@Configuration
public class SolrProxyServletConfiguration implements EnvironmentAware {
    /**
     * Servlet registration bean servlet registration bean.
     *
     * @return the servlet registration bean
     */
    @Bean(name = "solrProxyServlet")
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), propertyResolver.getProperty("servlet_url"));
        servletRegistrationBean.addInitParameter("targetUri", propertyResolver.getProperty("target_url"));
        servletRegistrationBean.addInitParameter("log", propertyResolver.getProperty("logging_enabled"));
        return servletRegistrationBean;
    }

    /**
     * The Property resolver.
     */
    private RelaxedPropertyResolver propertyResolver;

    /**
     * Sets environment.
     *
     * @param environment the environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "proxy.solr.");
    }
}
