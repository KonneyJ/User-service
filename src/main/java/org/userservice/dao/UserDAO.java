package org.userservice.dao;

import org.userservice.model.UserEntity;

import java.util.Collection;

/**
 * Data Access Object (DAO) интерфейс для управления сущностями пользователей.
 *
 * Предоставляет CRUD‑операции для работы с пользователями в хранилище данных.
 * Реализация интерфейса должна обеспечивать:
 * - создание новых пользователей;
 * - обновление существующих записей;
 * - удаление пользователей по идентификатору;
 * - получение пользователей по ID или всех пользователей.
 *
 * @see UserEntity
 */
public interface UserDAO {
    /**
     * Создаёт нового пользователя в системе.
     *
     * @param user объект пользователя, который необходимо сохранить.
     *             Не должен быть null.
     * @return сохранённый объект UserEntity с присвоенным ID.
     *         Возвращаемый объект не может быть null.
     * @throws IllegalArgumentException если пользователь null
     *         или содержит некорректные данные
     * @throws RuntimeException если произошла ошибка при сохранении
     *
     */
    UserEntity createUser(UserEntity user);

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param id идентификатор пользователя, которого необходимо обновить.
     * @param user новый объект пользователя с обновлёнными данными.
     *             Не должен быть null.
     * @return обновлённый объект UserEntity. Возвращаемый объект
     *         не может быть null.
     * @throws IllegalArgumentException если id <= 0 или user == null
     * @throws RuntimeException если произошла ошибка при обновлении
     *
     */
    UserEntity updateUser(int id, UserEntity user);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя для удаления.
     * @return true, если пользователь был успешно удалён;
     *         false, если пользователь с таким ID не существовал.
     * @throws IllegalArgumentException если id <= 0
     * @throws RuntimeException если произошла ошибка при удалении
     *
     */
    boolean deleteUser(int id);

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return объект UserEntity, если пользователь найден;
     *         null, если пользователь не существует.
     * @throws IllegalArgumentException если id <= 0
     * @throws RuntimeException если произошла ошибка при запросе
     *
     */
    UserEntity getUserById(int id);

    /**
     * Возвращает коллекцию всех пользователей в системе.
     *
     * @return коллекция объектов UserEntity. Может быть пустой,
     *         если в системе нет пользователей. Не может быть null.
     * @throws RuntimeException если произошла ошибка при получении данных
     *
     */
    Collection<UserEntity> getAllUsers();
}
