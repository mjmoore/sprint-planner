package io.mjmoore.issue.service;

import io.mjmoore.issue.dto.StoryDto;
import io.mjmoore.issue.model.Issue;
import io.mjmoore.issue.model.Story;
import io.mjmoore.issue.repository.IssueRepository;
import io.mjmoore.issue.repository.StoryRepository;
import io.mjmoore.issue.validation.EstimationValidation;
import io.mjmoore.issue.validation.StoryStatusValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StoryService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryStatusValidation storyValidator;

    @Autowired
    private EstimationValidation estimationValidation;

    private final ModelMapper mapper = new ModelMapper();

    public StoryService() {
        this.mapper.getConfiguration().setSkipNullEnabled(true);
    }

    public Story createStory(final StoryDto story) {
        final Issue issue = issueRepository.save(Issue.fromDto(story));
        return storyRepository.save(Story.fromDto(issue, story));
    }

    public Story completeStory(final Long storyId) throws ResponseStatusException {
        final Story story = storyRepository.findById(storyId).orElseThrow();
        if(story.getStatus() == Story.Status.Completed) {
            return story;
        }
        storyValidator.validateCompletable(story);
        story.setStatus(Story.Status.Completed);
        return storyRepository.save(story);
    }

    public Story estimateStory(final Long storyId, final Integer estimation) {
        final Story story = storyRepository.findById(storyId).orElseThrow();

        estimationValidation.validateStatus(story);
        story.setEstimation(estimation);
        return storyRepository.save(story);
    }

    public Story updateStory(final StoryDto storyDto, final Long storyId) {
        final Story story = storyRepository.findById(storyId).orElseThrow();
        mapper.map(storyDto, story);
        mapper.map(storyDto, story.getIssue());
        return storyRepository.save(story);
    }
}