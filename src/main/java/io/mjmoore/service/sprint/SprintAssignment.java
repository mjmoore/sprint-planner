package io.mjmoore.service.sprint;

import io.mjmoore.model.Story;
import io.mjmoore.service.SprintService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SprintAssignment implements Predicate<Story>, Comparable<SprintAssignment> {

    private final long sprintCapacity;
    private final int sprintNumber;
    private final List<Story> stories = new ArrayList<>();

    private long currentCapacity = 0;

    public SprintAssignment(final long sprintCapacity, final Story story, final int sprintNumber) {
        this.sprintCapacity = sprintCapacity;
        this.sprintNumber = sprintNumber;
        this.stories.add(story);
        currentCapacity += story.getEstimate();
    }

    @Override
    public boolean test(final Story story) {
        return currentCapacity + story.getEstimate() <= sprintCapacity;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void addStory(final Story story) {
        this.stories.add(story);
        currentCapacity += story.getEstimate();
    }

    public boolean hasRemainingCapacity() {
        return currentCapacity < sprintCapacity;
    }

    @Override
    public int compareTo(final SprintAssignment sprint) {
        return Integer.compare(sprintNumber, sprint.sprintNumber);
    }
}
