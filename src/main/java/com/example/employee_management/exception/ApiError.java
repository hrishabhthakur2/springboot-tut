package com.example.employee_management.exception;
import java.time.LocalDateTime;

public class ApiError {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;

    public ApiError(int status, String error, String message, LocalDateTime timestamp, String path){
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public int getStatus(){
        return status;
    }

    public String getError(){
        return error;
    }

    public String getMessage(){
        return message;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public String getPath(){
        return path;
    }
} 
