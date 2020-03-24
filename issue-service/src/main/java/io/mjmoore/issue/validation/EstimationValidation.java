package io.mjmoore.issue.validation;

import io.mjmoore.issue.model.Story;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class EstimationValidation {

    // Do not allow estimation of completed stories
    private final EnumSet<Story.Status> validStoriesForEstimation =
            EnumSet.of(Story.Status.New, Story.Status.Estimated);

    public void validateStatus(final Story story) {
        if(validStoriesForEstimation.contains(story.getStatus())) {
            return;
        }

        final String message = String.format(
                "Story in status %s cannot be estimated", story.getStatus());
        throw new RestError.BadRequest(message);
    }
}
