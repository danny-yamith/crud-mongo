package com.example.springmongo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class EmployeeException
    extends RuntimeException {

    private static final long serialVersionUID = -8375350453768259391L;

    private final HttpStatus status;

    public EmployeeException(String errorMessage, HttpStatus eStatus) {
        super(errorMessage);
        this.status = eStatus;
    }

}
