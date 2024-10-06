package com.example.springProject.Exception;

public class AgentManagementException extends Exception {
    public AgentManagementException() {
    }

    public AgentManagementException(String message) {
        super(message);
    }

    public AgentManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentManagementException(Throwable cause) {
        super(cause);
    }
}
