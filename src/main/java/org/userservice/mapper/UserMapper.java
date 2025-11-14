package org.userservice.mapper;

import org.springframework.stereotype.Component;
import org.userservice.dto.UserDto;
import org.userservice.model.UserEntity;

@Component
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
