package io.mjmoore.issue.validation;

import io.mjmoore.issue.model.Story;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StoryStatusValidationTest {

    private StoryStatusValidation validator = new StoryStatusValidation();

    @Test
    public void validateNotCompletable() {
        final Story story = new Story();

        assertThrows(ResponseStatusException.class, () -> {
            validator.validateCompletable(story);
        });

        story.setStatus(Story.Status.New);
        assertThrows(ResponseStatusException.class, () -> {
            validator.validateCompletable(story);
        });

        story.setStatus(Story.Status.Completed);
        assertThrows(ResponseStatusException.class, () -> {
            validator.validateCompletable(story);
        });
    }

    @Test
    public void validateCompletable() {
        final Story story = new Story();
        story.setStatus(Story.Status.Estimated);

        assertDoesNotThrow(() -> {
            validator.validateCompletable(story);
        });
    }

    @Test
    public void httpStatusCode() {
        final Story story = new Story();
        try {
            validator.validateCompletable(story);
        } catch(ResponseStatusException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
}
