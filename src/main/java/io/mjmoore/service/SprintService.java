package io.mjmoore.service;

import io.mjmoore.dto.SprintDto;
import io.mjmoore.model.Story;
import io.mjmoore.repository.StoryRepository;
import io.mjmoore.repository.UserRepository;
import io.mjmoore.service.sprint.EstimationLimit;
import io.mjmoore.service.sprint.SprintCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final long sprintCapacity = userRepository.count() * devCapacity;

        return storyRepository.getEstimatedStories().stream().collect(new SprintCollector(sprintCapacity));
    }
}
