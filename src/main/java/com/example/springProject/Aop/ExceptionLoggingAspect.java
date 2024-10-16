package com.example.springProject.Aop;

import com.example.springProject.Exception.AgentManagementException;
import com.example.springProject.Exception.AgentNotFoundException;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Exception.ProviderNotFoundException;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {
    static Logger log = Logger.getLogger("com.example.springProject.Aop.ExceptionLoggingAspect");

    @Around("execution(* com.example.springProject.Controller.*.*(..))")
    public Object logException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (AgentManagementException e) {
            log.error("AgentManagementException in method: " + joinPoint.getSignature() + ", message: " + e.getMessage());
            throw e;
        } catch (AgentNotFoundException e) {
            log.error("AgentNotFoundException in method: " + joinPoint.getSignature() + ", message: " + e.getMessage());
            throw e;
        } catch (ProviderManagementException e) {
            log.error("ProviderManagementException in method: " + joinPoint.getSignature() + ", message: " + e.getMessage());
            throw e;
        } catch (ProviderNotFoundException e) {
            log.error("ProviderNotFoundException in method: " + joinPoint.getSignature() + ", message: " + e.getMessage());
            throw e;
        } catch (Throwable e) {
            log.error("Unexpected error in method: " + joinPoint.getSignature() + ", message: " + e.getMessage());
            throw e;
        }
    }
}
