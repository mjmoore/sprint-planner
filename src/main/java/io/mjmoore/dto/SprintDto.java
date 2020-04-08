package io.mjmoore.dto;

import io.mjmoore.model.Story;

import java.util.List;

public class SprintDto {

    private int sprintNumber;
    private List<Story> stories;

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(final int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(final List<Story> stories) {
        this.stories = stories;
    }
}
