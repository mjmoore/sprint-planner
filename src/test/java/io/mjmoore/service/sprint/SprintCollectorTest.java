package io.mjmoore.service.sprint;

import io.mjmoore.dto.SprintDto;
import io.mjmoore.model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SprintCollectorTest {

    private SprintCollector collector;

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
    public void everythingInOneSprint() {
        collector = new SprintCollector(100);

        final List<Story> stories = List.of(smallStory, bigStory, massiveStory);
        final List<SprintDto> result = stories.stream().collect(collector);
        assertEquals(1, result.size());
    }

    @Test
    public void multipleSprintsCreated() {
        collector = new SprintCollector(12);
        final List<Story> stories = cloneStory(bigStory, 3);
        final List<SprintDto> result = stories.stream().collect(collector);

        assertEquals(3, result.size());
        for(final SprintDto sprint : result) {
            assertEquals(1, sprint.getStories().size());
        }
    }

    @Test
    // Combiner functionality not actually used, but good to test anyway
    public void combineCollectorsIgnoreDuplicateSprintNames() {
        collector = new SprintCollector(12);
        final SprintCollector otherCollector = new SprintCollector(10);

        final SprintAccumulator acc = collector.supplier().get();
        collector.accumulator().accept(acc, smallStory);

        final SprintAccumulator otherAcc = otherCollector.supplier().get();
        otherCollector.accumulator().accept(otherAcc, smallStory);

        final List<SprintDto> result = otherCollector.finisher().apply(otherCollector.combiner().apply(acc, otherAcc));
        assertEquals(1, result.size());
    }

    @Test
    public void combineCollectorsAddUniqueSprints() {
        collector = new SprintCollector(12);
        final SprintCollector otherCollector = new SprintCollector(10);

        final SprintAccumulator acc = collector.supplier().get();
        collector.accumulator().accept(acc, smallStory);

        final SprintAccumulator otherAcc = otherCollector.supplier().get();
        otherCollector.accumulator().accept(otherAcc, bigStory);
        otherCollector.accumulator().accept(otherAcc, smallStory);

        final List<SprintDto> result = otherCollector.finisher().apply(otherCollector.combiner().apply(acc, otherAcc));
        assertEquals(3, result.size());

    }

    private List<Story> cloneStory(final Story story, final int times) {
        final List<Story> stories = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            stories.add(new Story(story));
        }
        return stories;
    }
}
