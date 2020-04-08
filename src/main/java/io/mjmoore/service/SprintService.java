package io.mjmoore.service;

import io.mjmoore.dto.SprintDto;
import io.mjmoore.model.Story;
import io.mjmoore.repository.StoryRepository;
import io.mjmoore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SprintService {


    private final int devCapacity;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    public SprintService(@Value("${app.dev-capacity}") final int devCapacity) {
        this.devCapacity = devCapacity;
    }

    public List<Story> getSprint() {

        final long devs = userRepository.count();

        final List<Story> stories = storyRepository.getEstimatedStories();
        final EstimationLimit limit = new EstimationLimit(devs, devCapacity);

        return stories.stream().filter(limit).collect(Collectors.toList());
    }

    public List<SprintDto> getSprints() {
        return Collections.emptyList();
    }

    /**
     * Stateful filter for calculating capacity
     */
    public static class EstimationLimit implements Predicate<Story> {

        private final int devCapacity;
        private long capacityLimit;
        private long capacity = 0;

        public EstimationLimit(final long devs, final int devCapacity) {
            this.devCapacity = devCapacity;
            this.capacityLimit = devs * devCapacity;
        }

        @Override
        public boolean test(final Story story) {

            final boolean capacityAvailable = capacity + story.getEstimate() <= capacityLimit;
            final boolean devCapable = devCapacity >= story.getEstimate();

            final boolean completable = capacityAvailable && devCapable;

            if(completable) {
                capacity += story.getEstimate();
            }

            return completable;
        }
    }

}
