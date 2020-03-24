package io.mjmoore.issue.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mjmoore.issue.dto.StoryDto;
import io.mjmoore.issue.model.Story;
import io.mjmoore.issue.service.StoryService;
import io.mjmoore.issue.validation.RestError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class StoryControllerTest {

    private final StoryDto dto = new StoryDto();
    private final Story story = new Story();
    private final ObjectMapper mapper = new ObjectMapper();

    private final RestError.BadRequest badRequest
            = new RestError.BadRequest("bad request");

    @MockBean
    private StoryService storyService;

    @Autowired
    private MockMvc storyController;

    @Test
    public void createStory() throws Exception {
        when(storyService.completeStory(anyLong()))
                .thenReturn(story);

        storyController.perform(
                post("/stories/")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void completeStory() throws Exception {
        when(storyService.completeStory(anyLong()))
                .thenReturn(story);

        storyController.perform(
                post("/stories/100/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void completeNonExistentStory() throws Exception {
        when(storyService.completeStory(anyLong()))
                .thenThrow(new NoSuchElementException());

        storyController.perform(
                post("/stories/100/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void completeInvalidStory() throws Exception {
        when(storyService.completeStory(anyLong()))
                .thenThrow(badRequest);

        storyController.perform(
                post("/stories/100/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateStory() throws Exception {
        when(storyService.updateStory(any(StoryDto.class), anyLong()))
                .thenReturn(story);

        storyController.perform(
                patch("/stories/100")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateNonExistentStory() throws Exception {
        when(storyService.updateStory(any(StoryDto.class), anyLong()))
                .thenThrow(badRequest);

        storyController.perform(
                patch("/stories/100")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void estimateStory() throws Exception {
        when(storyService.estimateStory(anyLong(), anyInt()))
                .thenReturn(story);

        storyController.perform(
                patch("/stories/100/estimate/1")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void estimateInvalidStory() throws Exception {
        when(storyService.updateStory(any(StoryDto.class), anyLong()))
                .thenThrow(badRequest);

        storyController.perform(
                patch("/stories/100/estimate/1")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void estimateNonExistentStory() throws Exception {
        when(storyService.updateStory(any(StoryDto.class), anyLong()))
                .thenThrow(new NoSuchElementException());

        storyController.perform(
                patch("/stories/100/estimate/1")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
