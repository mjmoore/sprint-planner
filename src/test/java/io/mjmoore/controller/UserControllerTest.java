package io.mjmoore.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mjmoore.user.dto.UserDto;
import io.mjmoore.user.model.User;
import io.mjmoore.user.service.UserService;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private final UserDto dto = new UserDto("updated");
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc userController;

    @MockBean
    private UserService userService;

    @Test
    public void updateUser() throws Exception {

        final User user = new User(1L, dto.getName());

        when(userService.updateUser(any(UserDto.class), anyLong()))
                .thenReturn(user);

        userController.perform(
                patch("/users/" + user.getId())
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("updated")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void updateNonExistentUser() throws Exception {
        when(userService.updateUser(any(UserDto.class), anyLong()))
                .thenThrow(NoSuchElementException.class);

        userController.perform(
                patch("/users/10000")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
