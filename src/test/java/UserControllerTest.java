import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.userservice.controller.UserController;
import org.userservice.dto.UserDto;
import org.userservice.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mvc;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto.builder()
                .id(1)
                .name("Dima")
                .email("Dima@yandex.ru")
                .age((short) 35)
                .build();
    }

    @SneakyThrows
    @Test
    void createUserTest() {
        when(userService.createUser(any(UserDto.class)))
                .thenReturn(userDto);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.age", is(userDto.getAge()), Short.class));

        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @SneakyThrows
    @Test
    void updateUserTest() {
        int userId = userDto.getId();

        when(userService.updateUser(anyInt(), any(UserDto.class)))
                .thenReturn(userDto);

        mvc.perform(patch("/users/{id}", userId)
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.age", is(userDto.getAge()), Short.class));

        verify(userService, times(1)).updateUser(anyInt(), any(UserDto.class));
    }

    @SneakyThrows
    @Test
    void getUserByIdTest() {
        int userId = userDto.getId();

        when(userService.getUserById(userId)).thenReturn(userDto);

        mvc.perform(get("/users/{id}", userId)
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.age", is(userDto.getAge()), Short.class));

        verify(userService, times(1)).getUserById(userId);
    }

    @SneakyThrows
    @Test
    void getAllUsersTest() {
        Collection<UserDto> users = List.of(userDto);

        when(userService.getAllUsers()).thenReturn(users);

        mvc.perform(get("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(users.size())))
                .andExpect(jsonPath("$[0].id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].name", is(userDto.getName())))
                .andExpect(jsonPath("$[0].email", is(userDto.getEmail())))
                .andExpect(jsonPath("$[0].age", is(userDto.getAge()), Short.class));

        verify(userService, times(1)).getAllUsers();
    }

    @SneakyThrows
    @Test
    void deleteUserTest() {
        int userId = userDto.getId();

        mvc.perform(delete("/users/{id}", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(userId);
    }
}