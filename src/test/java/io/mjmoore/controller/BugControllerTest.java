package io.mjmoore.issue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mjmoore.issue.dto.BugDto;
import io.mjmoore.issue.model.Bug;
import io.mjmoore.issue.service.BugService;
import io.mjmoore.issue.validation.EstimationValidation;
import io.mjmoore.issue.validation.RestError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BugControllerTest {

    private final BugDto dto = new BugDto();
    private final Bug bug = new Bug();
    private final ObjectMapper mapper = new ObjectMapper();

    private final RestError.BadRequest badRequest
            = new RestError.BadRequest("bad request");

    @Autowired
    private MockMvc bugController;

    @MockBean
    private EstimationValidation validation;

    @MockBean
    private BugService bugService;


    @Test
    public void createBug() throws Exception {

        when(bugService.createBug(any(BugDto.class)))
                .thenReturn(bug);

        bugController.perform(
                post("/bugs/")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verifyBug() throws Exception {
        when(bugService.verifyBug(anyLong()))
                .thenReturn(bug);

        bugController.perform(post("/bugs/100/verify"))
                .andExpect(status().isOk());
    }

    @Test
    public void verifyInvalidBug() throws Exception {
        when(bugService.verifyBug(anyLong()))
                .thenThrow(badRequest);

        final MockHttpServletResponse response = bugController.perform(
                    post("/bugs/100/verify"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertEquals(badRequest.getReason(), response.getErrorMessage());
    }

    @Test
    public void verifyNonExistentBug() throws Exception {
        when(bugService.verifyBug(anyLong()))
                .thenThrow(new NoSuchElementException());

        bugController.perform(post("/bugs/100/verify"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void resolveBug() throws Exception {
        when(bugService.resolveBug(anyLong()))
                .thenReturn(bug);

        bugController.perform(post("/bugs/100/resolve"))
                .andExpect(status().isOk());
    }

    @Test
    public void resolveInvalidBug() throws Exception {
        when(bugService.resolveBug(anyLong()))
                .thenThrow(badRequest);

        final MockHttpServletResponse response = bugController.perform(
                    post("/bugs/100/resolve"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertEquals(badRequest.getReason(), response.getErrorMessage());
    }

    @Test
    public void resolveNonExistentBug() throws Exception {
        when(bugService.resolveBug(anyLong()))
                .thenThrow(new NoSuchElementException());

        bugController.perform(post("/bugs/100/resolve"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBug() throws Exception {
        when(bugService.updateBug(any(BugDto.class), anyLong()))
                .thenReturn(bug);

        bugController.perform(
                patch("/bugs/100")
                    .content(mapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateNonExistentBug() throws Exception {
        when(bugService.updateBug(any(BugDto.class), anyLong()))
                .thenThrow(new NoSuchElementException());

        bugController.perform(
                    patch("/bugs/100")
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
