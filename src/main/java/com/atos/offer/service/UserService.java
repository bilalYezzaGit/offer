package com.atos.offer.service;

import com.atos.offer.dto.UserDto;
import com.atos.offer.exception.UserAlreadyExistsException;
import com.atos.offer.exception.UserNotFoundException;
import com.atos.offer.mapper.UserMapper;
import com.atos.offer.model.User;
import com.atos.offer.model.UserId;
import com.atos.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * User Service
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    /**
     * Fetching user details
     *
     * @param userName  userName of the person
     * @param birthDate birthDate of the person
     * @param country   country of the person
     * @return user sought
     */
    public UserDto getUserById(String userName, LocalDate birthDate, String country) {
        UserId userId = new UserId(userName, birthDate, country);
        return userRepository
                .findById(userId)
                .map(UserMapper.INSTANCE::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * service method to create a user
     *
     * @param userDto specified user
     * @return
     */
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }
}
