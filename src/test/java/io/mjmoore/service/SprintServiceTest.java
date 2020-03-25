package io.mjmoore.service;

import io.mjmoore.model.Story;
import io.mjmoore.model.User;
import io.mjmoore.repository.StoryRepository;
import io.mjmoore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SprintServiceTest {

    private User user;
    private Story smallStory;
    private Story bigStory;
    private Story massiveStory;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private final SprintService sprintService = new SprintService(10);

    @BeforeEach
    public void setup() {
        user = new User();
        smallStory = new Story(2);
        bigStory = new Story(10);
        massiveStory = new Story(20);
    }

    @Test
    public void emptySystem() {
        when(userRepository.count()).thenReturn(0L);
        when(storyRepository.getEstimatedStories()).thenReturn(Collections.emptyList());

        assertTrue(sprintService.getSprint().isEmpty());
    }

    @Test
    public void noUserSystem() {
        when(userRepository.count()).thenReturn(0L);

        final List<Story> stories = createPopulatedList(smallStory, 10);
        when(storyRepository.getEstimatedStories()).thenReturn(stories);

        assertTrue(sprintService.getSprint().isEmpty());
    }

    @Test
    public void noStorySystem() {
        when(userRepository.count()).thenReturn(10L);
        when(storyRepository.getEstimatedStories()).thenReturn(Collections.emptyList());

        assertTrue(sprintService.getSprint().isEmpty());
    }

    @Test
    public void storyTooBig() {
        when(userRepository.count()).thenReturn(2L);

        when(storyRepository.getEstimatedStories())
                .thenReturn(List.of(massiveStory));

        assertTrue(sprintService.getSprint().isEmpty());
    }

    @Test
    public void tooManyStories() {
        when(userRepository.count()).thenReturn(1L);

        final List<Story> stories = createPopulatedList(bigStory, 10);

        when(storyRepository.getEstimatedStories()).thenReturn(stories);

        assertEquals(1, sprintService.getSprint().size());
    }

    @Test
    public void manySmallStories() {
        when(userRepository.count()).thenReturn(4L);

        final List<Story> stories = createPopulatedList(smallStory, 20);

        when(storyRepository.getEstimatedStories()).thenReturn(stories);

        assertEquals(stories.size(), sprintService.getSprint().size());
    }

    @Test
    public void manySmallSomeBigStories() {
        when(userRepository.count()).thenReturn(4L);

        final int smallStoriesAmount = 20;
        final List<Story> stories = createPopulatedList(smallStory, smallStoriesAmount);
        stories.addAll(createPopulatedList(bigStory, 2));

        when(storyRepository.getEstimatedStories()).thenReturn(stories);

        assertEquals(smallStoriesAmount, sprintService.getSprint().size());
    }

    @Test
    public void tooBigStoriesWithCompletableStories() {
        when(userRepository.count()).thenReturn(4L);

        final int smallStoriesAmount = 10;
        final int bigStoriesAmount = 2;
        final List<Story> stories = createPopulatedList(massiveStory, 2);
        stories.addAll(createPopulatedList(smallStory, smallStoriesAmount));
        stories.addAll(createPopulatedList(massiveStory, 2));
        stories.addAll(createPopulatedList(bigStory, bigStoriesAmount));
        stories.addAll(createPopulatedList(massiveStory, 2));

        when(storyRepository.getEstimatedStories()).thenReturn(stories);

        // All big and small stories present, massive stories skipped
        assertEquals(bigStoriesAmount + smallStoriesAmount, sprintService.getSprint().size());
    }

    @Test
    public void smallStoriesHidingAtTheEnd() {
        when(userRepository.count()).thenReturn(2L);

        final int smallStoriesAmount = 4;
        final int bigStoriesAmount = 20;
        final List<Story> stories = createPopulatedList(massiveStory, 2);
        stories.addAll(createPopulatedList(smallStory, smallStoriesAmount / 2));
        stories.addAll(createPopulatedList(bigStory, bigStoriesAmount));
        stories.addAll(createPopulatedList(smallStory, smallStoriesAmount / 2));

        when(storyRepository.getEstimatedStories()).thenReturn(stories);

        // Expect all small stories (2 * 4 = 8) and one big story (1 * 10 = 10) to fit capacity
        assertEquals(smallStoriesAmount + 1, sprintService.getSprint().size());
    }

    private <T> List<T> createPopulatedList(final T t, final int amount) {
        return IntStream.range(0, amount).mapToObj(i -> t).collect(Collectors.toList());
    }
}
