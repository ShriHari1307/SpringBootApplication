package com.example.springProject.ExceptionHandler;

import com.example.springProject.Exception.AgentManagementException;
import com.example.springProject.Exception.AgentNotFoundException;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Exception.ProviderNotFoundException;
import com.example.springProject.Response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.apache.log4j.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    static Logger log = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProviderManagementException.class)
    public ResponseEntity<Object> handleProviderManagementException(ProviderManagementException exception) {
        log.error("ProviderManagementException: "+exception);
        return ResponseHandler.getResponse("ProviderManagementException: "+exception.getMessage(),HttpStatus.BAD_REQUEST,null);
    }
    @ExceptionHandler(AgentManagementException.class)
    public ResponseEntity<Object> handleAgentManagementException(AgentManagementException exception) {
        log.error("AgentManagementException: "+exception);
        return ResponseHandler.getResponse("AgentManagementException: " + exception.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<Object> handleProviderNotFoundException(ProviderNotFoundException exception) {
        log.error("ProviderNotFoundException: "+exception);
        return ResponseHandler.getResponse("ProviderNotFoundException: " + exception.getMessage(), HttpStatus.NOT_FOUND, null);
    }
    @ExceptionHandler(AgentNotFoundException.class)
    public ResponseEntity<Object> handleAgentNotFoundException(AgentNotFoundException exception) {
        log.error("AgentNotFoundException: "+exception);
        return ResponseHandler.getResponse("AgentNotFoundException: " + exception.getMessage(), HttpStatus.NOT_FOUND, null);
    }
}
