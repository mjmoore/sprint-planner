package io.mjmoore.service;

import io.mjmoore.dto.UserDto;
import io.mjmoore.model.User;
import io.mjmoore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final User user = new User(1L, "test");

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private final UserService userService = new UserService();

    @Test
    public void updateUser() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        final UserDto userDto = new UserDto("update");
        final User updatedUser = userService.updateUser(userDto, user.getId());

        assertEquals(updatedUser.getName(), userDto.getName());
    }

    @Test
    public void updateNonExistentUser() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userService.updateUser(new UserDto(), 1L);
        });
    }
}
