package org.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.userservice.dto.UserDto;
import org.userservice.exception.UserNotFoundException;
import org.userservice.mapper.UserMapper;
import org.userservice.model.UserEntity;
import org.userservice.repository.UserRepository;
import org.userservice.service.kafka.UserServicePublish;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceJpaImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserServicePublish userServicePublish;

    // Создание пользователя
    @Override
    public UserDto createUser(UserDto user) {
        log.info("Получение запроса на создание пользователя в слое сервис {}", user);
        UserDto userDto = userMapper.toUserDto(userRepository.save(userMapper.toUser(user)));
        userServicePublish.publishUserCreated(user.getEmail());

        return userDto;
    }

    // Обновление пользователя
    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        log.info("Получение запроса на обновление пользователя в слое сервис {}", userDto);
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с id = " + id + " не найден"));
        userDto.setId(user.getId());

        return userMapper.toUserDto(userRepository.save(userMapper.toUser(userDto)));
    }

    // Удаление пользователя по id
    @Override
    public boolean deleteUser(int id) {
        log.info("Получение запроса на удаление пользователя в слое сервис с id {}", id);
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с id = " + id + " не найден"));
        userRepository.deleteById(id);
        userServicePublish.publishUserDeleted(user.getEmail());

        return true;
    }

    // Получение пользователя по id
    @Override
    public UserDto getUserById(int id) {
        log.info("Получение запроса на получение пользователя в слое сервис с id {}", id);

        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с id = " + id + " не найден")));
    }

    // Получение списка всех пользователей
    @Override
    public Collection<UserDto> getAllUsers() {
        log.info("Получение запроса на получение списка всех пользователей в слое сервис");
        Collection<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
