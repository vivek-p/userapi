package com.skillset.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends GenericException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(new ErrorCodeMessage(HttpStatus.NOT_FOUND.name(), message));
    }

    public NotFoundException(ErrorCodeMessage errorCodeMessage) {
        super(errorCodeMessage);
    }

}
