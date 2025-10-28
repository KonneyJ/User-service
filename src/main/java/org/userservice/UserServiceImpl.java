package org.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserDAO userDAO;

    // Создание пользователя
    @Override
    public UserDto createUser(UserDto user) {
        log.info("Получение запроса на создание пользователя в слое сервис {}", user);
        return mapper.toUserDto(userDAO.createUser(mapper.toUser(user)));
    }

    // Обновление пользователя
    @Override
    public UserDto updateUser(int id, UserDto user) {
        log.info("Получение запроса на обновление пользователя в слое сервис {}", user);
        return mapper.toUserDto(userDAO.updateUser(id, mapper.toUser(user)));
    }

    // Удлаение пользователя по id
    @Override
    public boolean deleteUser(int id) {
        log.info("Получение запроса на удаление пользователя в слое сервис с id {}", id);
        return userDAO.deleteUser(id);
    }

    // Получение пользователя по id
    @Override
    public UserDto getUserById(int id) {
        log.info("Получение запроса на получение пользователя в слое сервис с id {}", id);
        return mapper.toUserDto(userDAO.getUserById(id));
    }

    // Получение списка всех пользователей
    @Override
    public Collection<UserDto> getAllUsers() {
        log.info("Получение запроса на получение списка всех пользователей в слое сервис");
        Collection<User> users = userDAO.getAllUsers();
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
