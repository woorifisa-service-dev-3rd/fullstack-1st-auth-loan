package dev.service.cloud.loan.exception;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
