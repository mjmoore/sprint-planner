package io.mjmoore.service;

import io.mjmoore.dto.UserDto;
import io.mjmoore.model.User;
import io.mjmoore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper mapper = new ModelMapper();

    public UserService() {
        this.mapper.getConfiguration().setSkipNullEnabled(true);
    }

    public User updateUser(final UserDto userDto, final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        mapper.map(userDto, user);
        return userRepository.save(user);
    }
}
