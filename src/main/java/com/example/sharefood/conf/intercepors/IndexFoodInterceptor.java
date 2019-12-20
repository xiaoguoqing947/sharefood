package com.example.sharefood.conf.intercepors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IndexFoodInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String isAdmin = request.getParameter("isAdmin");
        if (isAdmin == null) {
            return true;
        } else if (("no".equals(isAdmin) || "user".equals(isAdmin)) && (request.getSession().getAttribute("admin") != null)) {
            return true;
        } else if (("yes".equals(isAdmin) || "admin".equals(isAdmin)) && (request.getSession().getAttribute("tip") != null)) {
            return true;
        }
        response.sendRedirect("/food");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
