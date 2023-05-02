package com.skillset.userapi.exception;

import lombok.Getter;

public class GenericException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private final ErrorCodeMessage errorCodeMessage;

    @Getter
    private String details;

    public GenericException(ErrorCodeMessage errorCodeMessage) {
        super(errorCodeMessage.getErrorMessage());
        this.errorCodeMessage = errorCodeMessage;
    }

    public GenericException(ErrorCodeMessage errorCodeMessage, String details) {
        super(errorCodeMessage.getErrorMessage());
        this.errorCodeMessage = errorCodeMessage;
        this.details = details;
    }
}
