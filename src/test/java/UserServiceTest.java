import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.userservice.dao.UserDAO;
import org.userservice.dto.UserDto;
import org.userservice.mapper.UserMapper;
import org.userservice.model.UserEntity;
import org.userservice.service.UserService;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserDto testUser;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        testUser = new UserDto();
        testUser.setId(1);
        testUser.setName("Julie");
        testUser.setEmail("julie@yandex.ru");
        testUser.setAge((short) 30);

        user.builder()
                .id(1)
                .name("Julie")
                .email("julie@yandex.ru")
                .age((short) 30)
                .build();
    }

    @Test
    void shouldReturnSavedUser() {
        // Given
        when(userDAO.createUser(any(UserEntity.class)))
                .thenReturn(user);

        // When
        UserDto result = userService.createUser(testUser);

        // Then
        Assertions.assertEquals(testUser.getId(), result.getId());
        Assertions.assertEquals(testUser.getName(), result.getName());
        Assertions.assertEquals(testUser.getEmail(), result.getEmail());
        Assertions.assertEquals(testUser.getAge(), result.getAge());

        verify(userDAO).createUser(user);
    }

    @Test
    void shouldReturnUpdatedUser() {
        // Given
        when(userDAO.updateUser(1, user))
                .thenReturn(user);

        // When
        UserDto result = userService.updateUser(1, testUser);

        // Then
        Assertions.assertEquals(testUser.getId(), result.getId());
        Assertions.assertEquals(testUser.getName(), result.getName());
        Assertions.assertEquals(testUser.getEmail(), result.getEmail());
        Assertions.assertEquals(testUser.getAge(), result.getAge());

        verify(userDAO).updateUser(1, user);
    }

    @Test
    void shouldReturnTrueWhenUserExists() {
        // Given
        when(userDAO.deleteUser(1))
                .thenReturn(true);

        // When
        boolean result = userService.deleteUser(1);

        // Then
        Assertions.assertEquals(true, result);

        verify(userDAO).deleteUser(1);
    }

    @Test
    void shouldReturnUserById() {
        // Given
        when(userDAO.getUserById(1))
                .thenReturn(user);

        // When
        UserDto result = userService.getUserById(1);

        // Then
        Assertions.assertEquals(testUser.getId(), result.getId());
        Assertions.assertEquals(testUser.getName(), result.getName());
        Assertions.assertEquals(testUser.getEmail(), result.getEmail());
        Assertions.assertEquals(testUser.getAge(), result.getAge());

        verify(userDAO).getUserById(1);
    }

    @Test
    void shouldReturnNullWhenNotFound() {
        // Given
        when(userDAO.getUserById(2)).thenReturn(null);

        // When
        UserDto result = userService.getUserById(2);

        // Then
        Assertions.assertNotEquals(testUser.getId(), result.getId());

        verify(userDAO).getUserById(2);
    }

    @Test
    void shouldReturnCollection() {
        // Given
        Collection<UserDto> users = Arrays.asList(testUser);
        Collection<UserEntity> usersList = Arrays.asList(user);
        when(userDAO.getAllUsers()).thenReturn(usersList);

        // When
        Collection<UserDto> result = userService.getAllUsers();

        // Then
        Assertions.assertEquals(users.size(), result.size());
        Assertions.assertEquals(1, result.size());

        verify(userDAO).getAllUsers();
    }
}
