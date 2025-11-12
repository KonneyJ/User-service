package org.userservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.userservice.model.UserEntity;

import java.util.List;

@Slf4j
public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Создание пользователя
    @Override
    public UserEntity createUser(UserEntity user) {
        log.info("Получение запроса на создание пользователя в слое DAO {}", user);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(user);
                transaction.commit();
                return user;
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка при сохранении пользователя", e);
            }
        }
    }

    // Обновление пользователя
    @Override
    public UserEntity updateUser(int id, UserEntity user) {
        log.info("Получение запроса на обновление пользователя в слое DAO {}", user);
        if ((user.getId() == 0 || id == 0) && id != user.getId()) {
            throw new IllegalArgumentException("Невозможно обновить пользователя без ID");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                UserEntity mergedUser = (UserEntity) session.merge(user);  // merge для detached-сущности
                transaction.commit();
                return mergedUser;
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка при обновлении пользователя", e);
            }
        }
    }

    // Удлаение пользователя по id
    @Override
    public boolean deleteUser(int id) {
        log.info("Получение запроса на удаление пользователя в слое DAO с id {}", id);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                UserEntity user = session.get(UserEntity.class, id);
                if (user == null) {
                    return false;  // Пользователь не найден
                }
                session.remove(user);
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка при удалении пользователя с ID: " + id, e);
            }
        }
    }

    // Получение пользователя по id
    @Override
    public UserEntity getUserById(int id) {
        log.info("Получение запроса на получение пользователя в слое DAO с id {}", id);
        try (Session session = sessionFactory.openSession()) {
            UserEntity user = session.get(UserEntity.class, id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении пользователя по ID: " + id, e);
        }
    }

    // Получение списка всех пользователей
    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Получение запроса на получение списка всех пользователей в слое DAO");
        try (Session session = sessionFactory.openSession()) {
            Query<UserEntity> query = session.createQuery("FROM User", UserEntity.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка пользователей", e);
        }
    }
}
