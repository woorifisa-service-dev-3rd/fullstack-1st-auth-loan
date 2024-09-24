package dev.service.cloud.loan.exception;

import lombok.Getter;

@Getter
public class RecommendException extends RuntimeException {
    private final ErrorCode errorCode;
    public RecommendException(ErrorCode errorCode, String message) {
        super(message); 
        this.errorCode = errorCode;
    }
}