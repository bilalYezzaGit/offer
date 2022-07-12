package com.atos.offer.controller;

import com.atos.offer.enums.Gender;
import com.atos.offer.exception.BadCountryException;
import com.atos.offer.exception.UserAlreadyExistsException;
import com.atos.offer.exception.UserNotFoundException;
import com.atos.offer.mapper.UserMapper;
import com.atos.offer.model.User;
import com.atos.offer.model.UserId;
import com.atos.offer.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    private final static LocalDate birthDate = LocalDate.of(1993, 1, 10);
    private final static String username = "username";
    private final static String country = "France";
    private final static String otherCountry = "Italy";
    private final static String phoneNumber = "0758624883";
    private final static Gender gender = Gender.M;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void should_get_user_when_user_exists() throws Exception {
        //Given
        User user = User
                .builder()
                .userId(new UserId(username, birthDate, country))
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();
        Mockito.when(userService.getUserById(any(), any(), any())).thenReturn(user);

        //When & Then
        this.mockMvc.perform(get("/users?userName={1}&birthDate={2}&country={3}", username, birthDate, country))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value(birthDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(phoneNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(gender.toString()));
    }

    @Test
    void should_throw_not_found_when_getting_user_and_user_does_not_exist() throws Exception {
        //Given
        UserId userId = new UserId(username, birthDate, country);
        UserNotFoundException exception = new UserNotFoundException(userId);
        Mockito.when(userService.getUserById(any(), any(), any())).thenThrow(exception);

        //When & Then
        this.mockMvc.perform(get("/users?userName={1}&birthDate={2}&country={3}", username, birthDate, country))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(exception.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    void should_throw_bad_request_when_getting_user_without_all_params() throws Exception {
        //Given
        //When & Then
        this.mockMvc.perform(get("/users?birthDate={2}&country={3}", username, birthDate, country))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Required request parameter 'userName' for method parameter type String is not present"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    void should_create_user_when_user_does_not_exist() throws Exception {
        //Given
        User user = User
                .builder()
                .userId(new UserId(username, birthDate, country))
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();
        Mockito.when(userService.createUser(any())).thenReturn(user);

        //When & Then
        this.mockMvc.perform(
                        post("/users")
                                .content(asJsonString(UserMapper.INSTANCE.toDto(user)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value(birthDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(phoneNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(gender.toString()));
    }


    @Test
    void should_not_create_user_when_user_already_exists() throws Exception {
        //Given
        UserId userId = new UserId(username, birthDate, country);
        User user = User
                .builder()
                .userId(userId)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException(userId);
        Mockito.when(userService.createUser(any())).thenThrow(userAlreadyExistsException);
        //When & Then
        this.mockMvc.perform(
                        post("/users")
                                .content(asJsonString(UserMapper.INSTANCE.toDto(user)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(userAlreadyExistsException.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }


    @Test
    void should_throw_exception_when_phone_is_wrongly_formatted() throws Exception {
        //Given
        UserId userId = new UserId(username, birthDate, country);
        User user = User
                .builder()
                .userId(userId)
                .gender(gender)
                .phoneNumber("1234")
                .build();
        //When & Then
        this.mockMvc.perform(
                        post("/users")
                                .content(asJsonString(UserMapper.INSTANCE.toDto(user)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation failed for argument userDto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }


    @Test
    void should_not_create_user_when_user_is_not_french() throws Exception {
        //Given
        UserId userId = new UserId(username, birthDate, otherCountry);
        User user = User
                .builder()
                .userId(userId)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();
        BadCountryException badCountryException = new BadCountryException(otherCountry);
        Mockito.when(userService.createUser(any())).thenThrow(badCountryException);
        //When & Then
        this.mockMvc.perform(
                        post("/users")
                                .content(asJsonString(UserMapper.INSTANCE.toDto(user)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(badCountryException.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    void should_not_create_user_when_user_is_under_18() throws Exception {
        //Given
        UserId userId = new UserId(username, LocalDate.of(2010, 1, 10), country);
        User user = User
                .builder()
                .userId(userId)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();
        //When & Then
        this.mockMvc.perform(
                        post("/users")
                                .content(asJsonString(UserMapper.INSTANCE.toDto(user)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation failed for argument userDto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }


    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}