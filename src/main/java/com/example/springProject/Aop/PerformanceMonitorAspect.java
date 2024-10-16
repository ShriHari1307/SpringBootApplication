package com.example.springProject.Aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitorAspect {

    static Logger log = Logger.getLogger("com.example.springProject.Aop.PerformanceMonitorAspect");

    @Around("execution(* com.example.springProject.Controller.ProviderController.*(..))")
    public Object testPerformanceMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Performance monitoring started");
        long startTime = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Performance monitoring completed. Execution time: " + (endTime - startTime) + "ms");
        return obj;
    }
}
