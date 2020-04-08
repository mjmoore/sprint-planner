package io.mjmoore.service.sprint;

import io.mjmoore.model.Story;
import io.mjmoore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SprintAccumulatorTest {

    private SprintAccumulator acc;

    private Story smallStory;
    private Story bigStory;
    private Story massiveStory;

    @BeforeEach
    public void setup() {
        smallStory = new Story(2);
        bigStory = new Story(10);
        massiveStory = new Story(20);
    }

    @Test
    public void storyTooBigForSprint() {
        acc = new SprintAccumulator(10);
        acc.addStoryToSprint(massiveStory);

        assertTrue(acc.getFullSprints().isEmpty());
        assertTrue(acc.getSprintsWithCapacity().isEmpty());
    }

    @Test
    public void initialSprintStoryBelowSprintCapacity() {
        acc = new SprintAccumulator(10);
        acc.addStoryToSprint(smallStory);

        assertEquals(1, acc.getSprintsWithCapacity().size());
        assertTrue(acc.getFullSprints().isEmpty());
    }

    @Test
    public void initialSprintStoryMatchingSprintCapacity() {
        acc = new SprintAccumulator(10);
        acc.addStoryToSprint(smallStory);

        assertEquals(1, acc.getSprintsWithCapacity().size());
        assertTrue(acc.getFullSprints().isEmpty());
    }

    @Test
    public void storyAddedToSprintWhenCapacityAvailable() {
        acc = new SprintAccumulator(12);
        acc.addStoryToSprint(smallStory);
        acc.addStoryToSprint(bigStory);

        assertEquals(1, acc.getFullSprints().size());
        assertEquals(2, acc.getFullSprints().stream().findFirst().get().getStories().size());

        assertTrue(acc.getSprintsWithCapacity().isEmpty());
    }

    @Test
    public void newStoryCreatedWhenNoCapacityAvailable() {
        acc = new SprintAccumulator(12);
        acc.addStoryToSprint(bigStory);

        assertEquals(1, acc.getSprintsWithCapacity().size());
        acc.addStoryToSprint(new Story(bigStory));
        assertEquals(2, acc.getSprintsWithCapacity().size());

        assertTrue(acc.getFullSprints().isEmpty());
    }

    @Test
    public void sprintCompletedWhenCapacityMatched() {
        acc = new SprintAccumulator(bigStory.getEstimate());
        acc.addStoryToSprint(bigStory);

        assertEquals(1, acc.getFullSprints().size());
        assertTrue(acc.getSprintsWithCapacity().isEmpty());

    }
}
