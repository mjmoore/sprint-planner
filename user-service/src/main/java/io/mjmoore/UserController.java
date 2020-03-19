package io.mjmoore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<User> getUsers(
            @RequestParam(required = false, defaultValue = "0") final String page,
            @RequestParam(required = false, defaultValue = "100") final String pageSize) {

        try {
            return userService.getUsers(Integer.parseInt(page), Integer.parseInt(pageSize));
        } catch(IllegalArgumentException e) {
            throw new InvalidRequestParameterException(e.getMessage());
        }
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable final Long id) {
        return userService.getUserById(id)
                .orElseThrow(NotFoundError::new);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody final User user) {
        return userService.addUser(user);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such user")
    private static class NotFoundError extends RuntimeException {}

    private static class InvalidRequestParameterException extends ResponseStatusException {

        public InvalidRequestParameterException(final String reason) {
            super(HttpStatus.BAD_REQUEST, reason);
        }
    }
}
