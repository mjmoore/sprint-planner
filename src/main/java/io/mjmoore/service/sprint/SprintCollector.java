package io.mjmoore.service.sprint;

import io.mjmoore.dto.SprintDto;
import io.mjmoore.model.Story;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SprintCollector implements Collector<Story, SprintAccumulator, List<SprintDto>> {

    private final long sprintCapacity;

    public SprintCollector(final long sprintCapacity) {
        this.sprintCapacity = sprintCapacity;
    }

    @Override
    public Supplier<SprintAccumulator> supplier() {
        return () -> new SprintAccumulator(sprintCapacity);
    }

    @Override
    public BiConsumer<SprintAccumulator, Story> accumulator() {
        return SprintAccumulator::addStoryToSprint;
    }

    @Override
    public BinaryOperator<SprintAccumulator> combiner() {
        return (first, second) -> {
            first.getFullSprints().addAll(second.getFullSprints());
            first.getSprintsWithCapacity().addAll(second.getSprintsWithCapacity());
            return first;
        };
    }

    @Override
    public Function<SprintAccumulator, List<SprintDto>> finisher() {
        return (acc) -> Stream.concat(acc.getFullSprints().stream(), acc.getSprintsWithCapacity().stream())
                .collect(Collectors.toList()).stream()
                .map(sprint -> new SprintDto(sprint.getStories()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
