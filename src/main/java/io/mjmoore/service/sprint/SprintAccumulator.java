package io.mjmoore.service.sprint;

import io.mjmoore.model.Story;

import java.util.Set;
import java.util.TreeSet;

public class SprintAccumulator {

    private final Set<SprintAssignment> fullSprints = new TreeSet<>();
    private final Set<SprintAssignment> sprintsWithCapacity = new TreeSet<>();

    private final long sprintCapacity;

    public SprintAccumulator(final long sprintCapacity) {
        this.sprintCapacity = sprintCapacity;
    }

    public void addStoryToSprint(final Story story) {

        // Stories which are too large should not be considered
        if(story.getEstimate() > sprintCapacity) {
            return;
        }

        // Handle initial sprint
        if(sprintsWithCapacity.isEmpty()) {
            createNewSprint(story);
            return;
        }

        // Find the first sprint which can can accept the story, or create a new sprint
        sprintsWithCapacity.stream()
                .filter(sprint -> sprint.test(story)).findFirst()
                .ifPresentOrElse(
                        sprint -> addStoryToSprint(sprint, story),
                        () -> createNewSprint(story));
    }

    public Set<SprintAssignment> getFullSprints() {
        return fullSprints;
    }

    public Set<SprintAssignment> getSprintsWithCapacity() {
        return sprintsWithCapacity;
    }

    /**
     * Add story to a specified sprint, removing it from further consideration if it can accept no more stories
     */
    private void addStoryToSprint(final SprintAssignment sprint, final Story story) {
        sprint.addStory(story);

        if(sprint.hasRemainingCapacity()) {
            return;
        }

        sprintsWithCapacity.remove(sprint);
        fullSprints.add(sprint);
    }

    private void createNewSprint(final Story story) {
        final int sprintNumber = fullSprints.size() + sprintsWithCapacity.size();
        final SprintAssignment sprint = new SprintAssignment(sprintCapacity, story, sprintNumber);

        if(story.getEstimate() == sprintCapacity) {
            fullSprints.add(sprint);
        } else {
            sprintsWithCapacity.add(sprint);
        }
    }
}
