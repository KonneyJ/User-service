package org.userservice.service;

import org.userservice.dto.UserDto;

import java.util.Collection;

/**
 * Сервис для бизнес‑логики управления пользователями.
 *
 * <p>
 * Предоставляет высокоуровневые операции над пользователями, включая:
 * - создание новых пользователей;
 * - обновление существующих записей;
 * - удаление пользователей по идентификатору;
 * - получение информации о пользователях (по ID или списком).
 * </p>
 *
 * <p>
 * Особенности реализации:
 * - Работает с DTO ({@link UserDto}) вместо сущностей БД для изоляции слоя бизнес‑логики.
 * </p>
 *
 * @see UserDto
 */

public interface UserService {
    /**
     * Создаёт нового пользователя в системе.
     */
    UserDto createUser(UserDto user);

    /**
     * Обновляет данные существующего пользователя.
     */
    UserDto updateUser(int id, UserDto user);

    /**
     * Удаляет пользователя по его идентификатору.
     */
    boolean deleteUser(int id);

    /**
     * Получает DTO пользователя по его идентификатору.
     */
    UserDto getUserById(int id);

    /**
     * Возвращает коллекцию всех пользователей в системе.
     */
    Collection<UserDto> getAllUsers();
}
