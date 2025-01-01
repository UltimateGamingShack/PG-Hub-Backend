package com.pghub.user.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorInfo {
    private LocalDateTime timestamp;
    private int errorCode;
    private String errorMessage;
}
