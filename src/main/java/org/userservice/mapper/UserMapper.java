package org.userservice.mapper;

import org.userservice.dto.UserDto;
import org.userservice.model.UserEntity;

public class UserMapper {
    public static UserDto toUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }

    public static UserEntity toUser(UserDto user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }
}
