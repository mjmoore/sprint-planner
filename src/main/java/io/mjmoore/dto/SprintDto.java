package io.mjmoore.dto;

import io.mjmoore.model.Story;

import java.util.ArrayList;
import java.util.List;

public class SprintDto {

    private final List<Story> stories = new ArrayList<>();

    public SprintDto(final List<Story> stories) {
        this.stories.addAll(stories);
    }

    public List<Story> getStories() {
        return stories;
    }
}