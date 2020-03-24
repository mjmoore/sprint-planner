package io.mjmoore.user.controller;

import io.mjmoore.user.dto.UserDto;
import io.mjmoore.user.model.User;
import io.mjmoore.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RepositoryRestController
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/users/{userId}")
    public @ResponseBody User updateUser(@RequestBody final UserDto userDto,
                                         @PathVariable final Long userId) throws NoSuchUserException {
        try {
            return userService.updateUser(userDto, userId);
        } catch(final NoSuchElementException e) {
            throw new NoSuchUserException(String.format("User %d does not exist", userId), e);
        }
    }

    private static class NoSuchUserException extends ResponseStatusException {
        public NoSuchUserException(final String message, final Throwable cause) {
            super(HttpStatus.NOT_FOUND, message, cause);
        }
    }
}
