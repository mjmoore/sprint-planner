package io.mjmoore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mjmoore.model.Story;
import io.mjmoore.service.SprintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SprintControllerTest {

    private final TypeReference<List<Story>> type = new TypeReference<>() {};
    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private SprintService sprintService;

    @Autowired
    private MockMvc storyController;


    @Test
    public void noStoriesForSprint() throws Exception {
        when(sprintService.getSprint()).thenReturn(Collections.emptyList());

        final String body = storyController.perform(get("/sprint"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<Story> result = mapper.readValue(body, type);
        assertTrue(result.isEmpty());
    }

    @Test
    public void storiesExistForSprint() throws Exception {
        final Story story = new Story();
        final List<Story> stories = List.of(story, story, story);

        when(sprintService.getSprint()).thenReturn(stories);

        final String body = storyController.perform(get("/sprint"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<Story> result = mapper.readValue(body, type);
        assertEquals(stories.size(), result.size());
    }
}
