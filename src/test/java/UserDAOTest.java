import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.userservice.dao.UserDAO;
import org.userservice.dao.UserDAOImpl;
import org.userservice.model.UserEntity;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class UserDAOTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testpostgresql")
                    .withUsername("testusername")
                    .withPassword("testpassword");

    private UserDAO userDAO;
    private SessionFactory sessionFactory;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl(sessionFactory);
        user.builder()
                .id(1)
                .name("Julie")
                .email("julie@yandex.ru")
                .age((short) 30)
                .build();
    }

    @Test
    void shouldCreateAndReturnUser() {
        // When
        UserEntity saved = userDAO.createUser(user);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isPositive();
        assertThat(saved.getName()).isEqualTo("Julie");
    }

    @Test
    void shouldUpdateUser() {
        // Given
        UserEntity saved = userDAO.createUser(user);
        int id = saved.getId();

        user.builder().name("Yulia");

        // When
        UserEntity updated = userDAO.updateUser(id, user);

        // Then
        assertThat(updated.getName()).isEqualTo("Yulia");
    }

    @Test
    void shouldRemoveUser() {
        // Given
        UserEntity saved = userDAO.createUser(user);
        int id = saved.getId();

        // When
        boolean deleted = userDAO.deleteUser(id);

        // Then
        assertThat(deleted).isTrue();

        Collection<UserEntity> all = userDAO.getAllUsers();
        assertThat(all).doesNotContain(user);
    }

    @Test
    void shouldReturnUserById() {
        // Given
        UserEntity saved = userDAO.createUser(user);
        int id = saved.getId();

        // When
        UserEntity found = userDAO.getUserById(id);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Julie");
    }

    @Test
    void shoulReturnAllUsers() {
        // Given
        UserEntity saved = userDAO.createUser(user);

        // When
        Collection<UserEntity> all = userDAO.getAllUsers();

        // Then
        assertThat(all).hasSize(1);
    }

    @Test
    void shouldReturnEmptyCollection() {
        // Given
        // Не добавляем пользователей в БД

        // When
        Collection<UserEntity> all = userDAO.getAllUsers();

        // Then
        assertThat(all).isEmpty();
    }
}
