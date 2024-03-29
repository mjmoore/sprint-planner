package io.mjmoore.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RestError {

    public static class BadRequest extends ResponseStatusException {
        public BadRequest(final String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }
}
