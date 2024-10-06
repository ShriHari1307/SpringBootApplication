package com.example.springProject.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Component
public class RoleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userRole = (String) request.getSession().getAttribute("role");
        System.out.println("User Role in RoleInterceptor: " + userRole);
        if (!"ADMIN".equals(userRole)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain");
            PrintWriter writer = response.getWriter();
            writer.write("Access Denied only admins");
            writer.flush();
            return false;
        }
        return true;
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            System.out.println("Inside postHandle - View Name: " + modelAndView.getViewName());
        } else {
            System.out.println("Inside postHandle - No View (possibly due to redirect or no response body)");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("Inside afterCompletion - Request Completed");

        if (ex != null) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }
    }
}
