package io.mjmoore.issue.validation;

import io.mjmoore.issue.model.Story;

import java.util.EnumSet;

public class EstimationValidation {

    private final int estimationMix = 1;
    private final int estimationMax = 10;

    // Do not allow estimation of completed stories
    private final EnumSet<Story.Status> validStoriesForEstimation =
            EnumSet.of(Story.Status.New, Story.Status.Estimated);

    public void validateEstimation(final Story story, final int estimation) {
        if (estimationInvalid(estimation)) {
            final String message = String.format(
                    "Estimation must be between %d and %d, got %d",
                    estimationMix, estimationMax, estimation);
            throw new RestError.BadRequest(message);
        }

        if(invalidStoryStatus(story)) {
            final String message = String.format(
                    "Story in status %s cannot be estimated", story.getStatus());
            throw new RestError.BadRequest(message);
        }
    }

    private boolean estimationInvalid(final int estimation) {
        return estimation > estimationMax || estimation < estimationMix;
    }

    private boolean invalidStoryStatus(final Story story) {
        return !validStoriesForEstimation.contains(story.getStatus());
    }
}
