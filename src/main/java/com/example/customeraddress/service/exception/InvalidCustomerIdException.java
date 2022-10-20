package com.example.customeraddress.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCustomerIdException extends ResponseStatusException {
    public InvalidCustomerIdException(HttpStatus status, @Nullable String reason, @Nullable Throwable cause) {
        super(status, reason, cause);
        Assert.notNull(status, "HttpStatus is required");
        int status1 = status.value();
    }

    public InvalidCustomerIdException() {
        super(HttpStatus.NOT_FOUND, "Customer not found, id not valid.");
    }
}