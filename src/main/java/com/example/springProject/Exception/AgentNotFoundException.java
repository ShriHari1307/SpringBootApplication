package com.example.springProject.Exception;

public class AgentNotFoundException extends Exception {
    public AgentNotFoundException() {
    }

    public AgentNotFoundException(String message) {
        super(message);
    }

    public AgentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentNotFoundException(Throwable cause) {
        super(cause);
    }
}
