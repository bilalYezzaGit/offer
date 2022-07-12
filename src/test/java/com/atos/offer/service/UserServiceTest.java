package com.atos.offer.service;

import com.atos.offer.enums.Gender;
import com.atos.offer.exception.BadCountryException;
import com.atos.offer.exception.UserAlreadyExistsException;
import com.atos.offer.exception.UserNotFoundException;
import com.atos.offer.model.User;
import com.atos.offer.model.UserId;
import com.atos.offer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class UserServiceTest {

    private final static LocalDate birthDate = LocalDate.of(1993, 1, 10);
    private final static String username = "username";
    private final static String country = "France";
    private final static String otherCountry = "Italy";
    private final static String phoneNumber = "0758624883";
    private final static Gender gender = Gender.M;

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void shoud_get_user_when_user_exists() {
        //Given
        User user = User
                .builder()
                .userId(new UserId(username, birthDate, country))
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //When
        User foundUser = userService.getUserById(username, birthDate, country);

        //then
        assertThat(foundUser.getUserId().getUserName()).isEqualTo(username);
        assertThat(foundUser.getUserId().getBirthDate()).isEqualTo(birthDate);
        assertThat(foundUser.getUserId().getCountry()).isEqualTo(country);
        assertThat(foundUser.getGender()).isEqualTo(gender);
        assertThat(foundUser.getPhoneNumber()).isEqualTo(phoneNumber);
    }


    @Test
    void shoud_throw_exception_when_get_not_existing_user() {
        //Given
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());

        //When
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUserById(username, birthDate, country));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("User: " + (new UserId(username, birthDate, country)) + " does not exist.");
    }


    @Test
    void shoud_throw_exception_when_creating_not_french_user() {
        //Given
        User user = User
                .builder()
                .userId(new UserId(username, birthDate, otherCountry))
                .build();

        //When
        BadCountryException exception = assertThrows(BadCountryException.class, () -> userService.createUser(user));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("Cannot register user with Country " + otherCountry + ".");
    }


    @Test
    void shoud_throw_exception_when_creating_existing_user() {
        //Given
        UserId userId = new UserId(username, birthDate, country);
        User user = User
                .builder()
                .userId(userId)
                .build();
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //When
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("user : " + userId + " already exists.");
    }


    @Test
    void shoud_save_new_french_user() {
        //Given
        UserId userId = new UserId(username, birthDate, country);
        User user = User
                .builder()
                .userId(userId)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .build();
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any())).thenReturn(user);

        //When
        User savedUser = userService.createUser(user);

        //then
        assertThat(savedUser.getUserId().getUserName()).isEqualTo(username);
        assertThat(savedUser.getUserId().getBirthDate()).isEqualTo(birthDate);
        assertThat(savedUser.getUserId().getCountry()).isEqualTo(country);
        assertThat(savedUser.getGender()).isEqualTo(gender);
        assertThat(savedUser.getPhoneNumber()).isEqualTo(phoneNumber);
    }

}