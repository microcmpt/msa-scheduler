package com.msa.scheduler.web;

import com.msa.scheduler.leaderselection.LeaderSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Resource handle proxy interceptor.
 *
 * @author sxp
 */
@Component
public class RequestHandleInterceptor extends HandlerInterceptorAdapter{
    /**
     * The Selector.
     */
    @Autowired
    private LeaderSelector selector;
    /**
     * The Proxy.
     */
    @Autowired
    private WebProxy proxy;

    /**
     * This implementation always returns {@code true}.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        return selector.hasLeader();
    }

    /**
     * This implementation is empty.
     *
     * @param request      the request
     * @param response     the response
     * @param handler      the handler
     * @param modelAndView the model and view
     * @throws Exception the exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * This implementation is empty.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @param ex       the ex
     * @throws Exception the exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
