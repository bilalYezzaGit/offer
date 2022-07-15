package com.atos.offer.mapper;

import com.atos.offer.dto.UserDto;
import com.atos.offer.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Map between business objects and entities.
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "userId.userName", source = "userDto.userName")
    @Mapping(target = "userId.birthDate", source = "userDto.birthDate")
    @Mapping(target = "userId.country", source = "userDto.country")
    @Mapping(target = "gender", source = "userDto.gender")
    User toEntity(UserDto userDto);


    @Mapping(target = "userName", source = "user.userId.userName")
    @Mapping(target = "birthDate", source = "user.userId.birthDate")
    @Mapping(target = "country", source = "user.userId.country")
    @Mapping(target = "gender", source = "user.gender")
    UserDto toDto(User user);
}
