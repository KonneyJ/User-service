package org.userservice;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Создание пользователя
    @Override
    public User createUser(User user) {
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
    public User updateUser(int id, User user) {
        log.info("Получение запроса на обновление пользователя в слое DAO {}", user);
        if ((user.getId() == 0 || id == 0) && id != user.getId()) {
            throw new IllegalArgumentException("Невозможно обновить пользователя без ID");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User mergedUser = (User) session.merge(user);  // merge для detached-сущности
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
                User user = session.get(User.class, id);
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
    public User getUserById(int id) {
        log.info("Получение запроса на получение пользователя в слое DAO с id {}", id);
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении пользователя по ID: " + id, e);
        }
    }

    // Получение списка всех пользователей
    @Override
    public List<User> getAllUsers() {
        log.info("Получение запроса на получение списка всех пользователей в слое DAO");
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка пользователей", e);
        }
    }
}
