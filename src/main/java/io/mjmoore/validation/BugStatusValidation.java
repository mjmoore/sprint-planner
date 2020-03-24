package io.mjmoore.validation;

import io.mjmoore.model.Bug;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BugStatusValidation {

    public void validate(final Bug bug, final Bug.Status status) throws ResponseStatusException {

        if(bug.getStatus().next() != status) {
            final String message = String.format(
                    "Status cannot change from %s to %s", bug.getStatus().name(), status);
            throw new RestError.BadRequest(message);
        }
    }
}
