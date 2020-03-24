package io.mjmoore.validation;

import io.mjmoore.model.Story;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EstimationValidationTest {

    private final EstimationValidation validator = new EstimationValidation();

    @Test
    public void testValidEstimationStatus() {
        final Story story = new Story();

        story.setStatus(Story.Status.New);
        assertDoesNotThrow(() -> {
            validator.validateStatus(story);
        });

        story.setStatus(Story.Status.Estimated);
        assertDoesNotThrow(() -> {
            validator.validateStatus(story);
        });
    }

    @Test
    public void testInvalidEstimationStatus() {
        final Story story = new Story();

        story.setStatus(Story.Status.Completed);
        assertThrows(RestError.BadRequest.class, () -> {
            validator.validateStatus(story);
        });
    }
}
