package io.mjmoore.issue.validation;

import io.mjmoore.issue.model.Bug;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BugStatusValidationTest {

    final BugStatusValidation validator = new BugStatusValidation();

    @Test
    public void testValidTransitions() {
        final Bug bug = new Bug();

        bug.setStatus(Bug.Status.New);
        assertDoesNotThrow(() -> {
            validator.validate(bug, Bug.Status.Verified);
        });

        bug.setStatus(Bug.Status.Verified);
        assertDoesNotThrow(() -> {
            validator.validate(bug, Bug.Status.Resolved);
        });

        bug.setStatus(Bug.Status.Resolved);
        assertDoesNotThrow(() -> {
            validator.validate(bug, Bug.Status.Resolved);
        });

    }

    @Test
    public void testInvalidTransitions() {
        final Bug bug = new Bug();

        bug.setStatus(Bug.Status.New);
        assertThrows(RestError.BadRequest.class, () -> {
            validator.validate(bug, Bug.Status.Resolved);
        });

        bug.setStatus(Bug.Status.Verified);
        assertThrows(RestError.BadRequest.class, () -> {
            validator.validate(bug, Bug.Status.New);
        });

        bug.setStatus(Bug.Status.Verified);
        assertThrows(RestError.BadRequest.class, () -> {
            validator.validate(bug, Bug.Status.New);
        });

        bug.setStatus(Bug.Status.Resolved);
        assertThrows(RestError.BadRequest.class, () -> {
            validator.validate(bug, Bug.Status.New);
        });

        bug.setStatus(Bug.Status.Resolved);
        assertThrows(RestError.BadRequest.class, () -> {
            validator.validate(bug, Bug.Status.Verified);
        });
    }

    @Test
    public void httpStatusCode() {
        final Bug bug = new Bug();
        bug.setStatus(Bug.Status.Resolved);
        try {
            validator.validate(bug, Bug.Status.New);
        } catch (RestError.BadRequest e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
}
