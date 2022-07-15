package com.atos.offer.controller;

import com.atos.offer.dto.UserDto;
import com.atos.offer.exception.UserAlreadyExistsException;
import com.atos.offer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * User controller. It enables creating and fetching users
 */
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    /**
     * Fetch user by its identifier (username + birthDate + country)
     *
     * @param userName  username of the person
     * @param birthDate birthdate of the person
     * @param country   residence country of the person
     * @return sought user
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<UserDto> getUser(
            @RequestParam @NotBlank String userName,
            @RequestParam @NotEmpty @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam @NotEmpty String country) {
        UserDto user = userService.getUserById(userName, birthDate, country);
        return ResponseEntity.ok(user);
    }

    /**
     * Save a new user
     *
     * @param userDto user details
     * @return user after saving
     * @throws UserAlreadyExistsException      when user already exists
     * @throws MethodArgumentNotValidException when a problem of param validation occurs
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

}
