package com.example.springProject.Exception;

public class ProviderManagementException extends Exception{
    public ProviderManagementException() {
    }

    public ProviderManagementException(String message) {
        super(message);
    }

    public ProviderManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderManagementException(Throwable cause) {
        super(cause);
    }
}
