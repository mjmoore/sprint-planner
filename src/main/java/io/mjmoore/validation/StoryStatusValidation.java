package io.mjmoore.issue.validation;

import io.mjmoore.issue.model.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryStatusValidation {

    public void validateCompletable(final Story story) throws RestError.BadRequest {

        if(story.getStatus() != Story.Status.Estimated) {
            final String message = String.format(
                    "Stories must be estimated before they can be completed. " +
                    "Current status: %s", story.getStatus().name());
            throw new RestError.BadRequest(message);
        }

    }
}
