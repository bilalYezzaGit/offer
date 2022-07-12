package com.atos.offer.controller;

import com.atos.offer.dto.UserDto;
import com.atos.offer.mapper.UserMapper;
import com.atos.offer.model.User;
import com.atos.offer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<UserDto> getUser(
            @RequestParam @NotBlank String userName,
            @RequestParam @NotEmpty @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam @NotEmpty String country) {
        User user = userService.getUserById(userName, birthDate, country);
        return ResponseEntity.ok(UserMapper.INSTANCE.toDto(user));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        return ResponseEntity.ok(UserMapper.INSTANCE.toDto(userService.createUser(user)));
    }

}
