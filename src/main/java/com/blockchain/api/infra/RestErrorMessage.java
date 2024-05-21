package com.blockchain.api.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class RestErrorMessage {
    private HttpStatus status;
    private String message;
}
