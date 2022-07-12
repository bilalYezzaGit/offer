package com.atos.offer.service;

import com.atos.offer.exception.BadCountryException;
import com.atos.offer.exception.UserAlreadyExistsException;
import com.atos.offer.exception.UserNotFoundException;
import com.atos.offer.model.User;
import com.atos.offer.model.UserId;
import com.atos.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User getUserById(String userName, LocalDate birthDate, String country) {
        UserId userId = new UserId(userName, birthDate, country);
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User createUser(User user) {
        if (!"France".equals(user.getUserId().getCountry())) {
            throw new BadCountryException(user.getUserId().getCountry());
        }
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        return userRepository.save(user);
    }
}
