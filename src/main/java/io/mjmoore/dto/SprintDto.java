package io.mjmoore.dto;

import java.util.List;

public class SprintDto {

    private String isoDate;
    private List<StoryDto> stories;

    public String getIsoDate() {
        return isoDate;
    }

    public void setIsoDate(final String isoDate) {
        this.isoDate = isoDate;
    }

    public List<StoryDto> getStories() {
        return stories;
    }

    public void setStories(final List<StoryDto> stories) {
        this.stories = stories;
    }
}
