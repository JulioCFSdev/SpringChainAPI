package com.blockchain.api.adapter.infra;

import com.blockchain.api.adapter.exceptions.InvalidBlockHashException;
import com.blockchain.api.adapter.exceptions.InvalidBlockIndexException;
import com.blockchain.api.adapter.exceptions.InvalidGenesisBlockException;
import com.blockchain.api.adapter.exceptions.InvalidPreviousHashException;
import com.blockchain.api.adapter.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidGenesisBlockException.class)
    private ResponseEntity<RestErrorMessage> handleInvalidGenesisBlockException(InvalidGenesisBlockException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidBlockIndexException.class)
    private ResponseEntity<RestErrorMessage> handleInvalidBlockIndexException(InvalidBlockIndexException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidBlockHashException.class)
    private ResponseEntity<RestErrorMessage> handleInvalidBlockHashException(InvalidBlockHashException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidPreviousHashException.class)
    private ResponseEntity<RestErrorMessage> handleInvalidPreviousHashException(InvalidPreviousHashException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
