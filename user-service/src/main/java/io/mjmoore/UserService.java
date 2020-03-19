package io.mjmoore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public Page<User> getUsers(final int page, final int pageSize) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public User addUser(final User user) {
        return userRepository.save(user);
    }
}
