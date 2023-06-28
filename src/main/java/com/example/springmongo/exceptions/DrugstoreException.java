package com.example.springmongo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class DrugstoreException
    extends RuntimeException {

    private static final long serialVersionUID = -640615521848112891L;

    private final HttpStatus status;

    public DrugstoreException(String errorMessage, HttpStatus eStatus) {
        super(errorMessage);
        this.status = eStatus;
    }

}
