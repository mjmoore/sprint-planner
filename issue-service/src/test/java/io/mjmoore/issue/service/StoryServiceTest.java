package io.mjmoore.issue.service;

import io.mjmoore.issue.dto.StoryDto;
import io.mjmoore.issue.model.Issue;
import io.mjmoore.issue.model.Story;
import io.mjmoore.issue.repository.IssueRepository;
import io.mjmoore.issue.repository.StoryRepository;
import io.mjmoore.issue.validation.StoryStatusValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoryServiceTest {

    private Issue issue;
    private Story story;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private StoryStatusValidation validator;

    @InjectMocks
    private final StoryService storyService = new StoryService();

    @BeforeEach
    public void setup() {
        issue = new Issue(1L, "Test story", "Test description");
        story = new Story(2L, issue);
    }

    @Test
    public void createValidStory() {
        when(issueRepository.save(any(Issue.class)))
                .thenReturn(issue);

        when(storyRepository.save(any(Story.class)))
                .thenReturn(story);

        final StoryDto storyDto = new StoryDto();
        storyDto.setTitle(issue.getTitle());
        storyDto.setDescription(issue.getDescription());

        final Story createdStory = storyService.createStory(storyDto);
        assertEquals(issue, createdStory.getIssue());
        assertEquals(Story.Status.New, createdStory.getStatus());
    }

    @Test
    public void completeValidStory() {
        when(storyRepository.findById(anyLong())).thenReturn(Optional.of(story));
        when(storyRepository.save(any(Story.class))).then(AdditionalAnswers.returnsFirstArg());

        story.setStatus(Story.Status.Estimated);
        final Story completedStory = storyService.completeStory(0L);
        assertEquals(Story.Status.Completed, completedStory.getStatus());
    }

    @Test
    public void completeCompletedStory() {
        when(storyRepository.findById(anyLong()))
                .thenReturn(Optional.of(story));

        story.setStatus(Story.Status.Completed);

        final Story completedStory = storyService.completeStory(0L);
        assertEquals(Story.Status.Completed, completedStory.getStatus());
        verify(storyRepository, times(0)).save(any(Story.class));
    }

    @Test
    public void updateStory() {
        when(storyRepository.findById(anyLong()))
                .thenReturn(Optional.of(story));

        when(storyRepository.save(any(Story.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        final StoryDto storyDto = new StoryDto();
        storyDto.setDescription("updated-description");
        storyDto.setTitle("updated-title");
        storyDto.setEstimation(1000);

        final Story updatedStory = storyService.updateStory(storyDto, 0L);
        assertEquals(1000, updatedStory.getEstimation());
        assertEquals(storyDto.getTitle(), updatedStory.getIssue().getTitle());
        assertEquals(storyDto.getDescription(), updatedStory.getIssue().getDescription());
    }

}
