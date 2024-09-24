package dev.service.cloud.loan.exception;

import lombok.Getter;

@Getter
public class LoanException extends RuntimeException {
    private ErrorCode errorCode;

    public LoanException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
