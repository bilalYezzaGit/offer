package com.atos.offer.mapper;

import com.atos.offer.dto.UserDto;
import com.atos.offer.enums.Gender;
import com.atos.offer.model.User;
import com.atos.offer.model.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final static LocalDate birthDate = LocalDate.of(1993, 1, 10);
    private final static String username = "username";
    private final static String country = "France";
    private final static String phoneNumber = "0758624883";
    private final static Gender gender = Gender.F;

    @Test
    void map_dto_to_entity() {
        //Given
        UserDto userDto = new UserDto(username, birthDate, country, phoneNumber, gender);

        //When
        User user = UserMapper.INSTANCE.toEntity(userDto);

        //then
        assertThat(user.getUserId().getUserName()).isEqualTo(username);
        assertThat(user.getUserId().getBirthDate()).isEqualTo(birthDate);
        assertThat(user.getUserId().getCountry()).isEqualTo(country);
        assertThat(user.getGender()).isEqualTo(gender);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void map_entity_to_dto() {
        //Given
        UserId userId = new UserId(username, birthDate, country);
        User userEntity = new User(userId, phoneNumber, gender);

        //When
        UserDto userDto = UserMapper.INSTANCE.toDto(userEntity);

        //then
        assertThat(userDto.getUserName()).isEqualTo(username);
        assertThat(userDto.getBirthDate()).isEqualTo(birthDate);
        assertThat(userDto.getCountry()).isEqualTo(country);
        assertThat(userDto.getGender()).isEqualTo(gender);
        assertThat(userDto.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void map_null_objects() {
        //Given
        User userEntity = null;
        UserDto userDto = null;

        //When
        UserDto convertedDto = UserMapper.INSTANCE.toDto(userEntity);
        User convertedEntity = UserMapper.INSTANCE.toEntity(userDto);

        //then
        assertThat(convertedDto).isNull();
        assertThat(convertedEntity).isNull();
    }


}