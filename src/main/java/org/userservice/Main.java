package org.userservice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Успешный запуск приложения");
        UserDAO userDAO = new UserDAOImpl(HibernateUtil.getSessionFactory());
        UserMapper mapper = new UserMapper();
        UserService userService = new UserServiceImpl(mapper, userDAO);

        // Создание нового пользователя
        log.info("Создание нового пользователя");
        UserDto user1 = UserDto.builder()
                .name("Иван")
                .email("ivan@yandex.ru")
                .age((short) 20)
                .build();
        log.info("Отправление запроса на создание пользователя {}", user1);
        UserDto createdUser1 = userService.createUser(user1);
        log.info("Пользователь успешно создан {}", createdUser1);
        System.out.println(createdUser1);

        // Создание еще одного нового пользователя
        log.info("Создание нового пользователя");
        UserDto user2 = UserDto.builder()
                .name("Марья")
                .email("mari@yandex.ru")
                .age((short) 18)
                .build();
        log.info("Отправление запроса на создание пользователя {}", user2);
        UserDto createdUser2 = userService.createUser(user2);
        log.info("Пользователь успешно создан {}", createdUser1);
        System.out.println(createdUser2);

        // Получение пользователей по id
        log.info("Получение пользователей по id");
        System.out.println(userService.getUserById(createdUser1.getId()));
        System.out.println(userService.getUserById(createdUser2.getId()));
        log.info("Пользователи успешно получены");

        //Получение списка всех пользователей
        log.info("Получение списка всех пользователей");
        System.out.println(userService.getAllUsers());
        log.info("Пользователи успешно получены");

        // Удаление пользователя по id
        log.info("Удаление пользователя по id");
        System.out.println(userService.deleteUser(createdUser1.getId()));
        log.info("Пользователь успешно удален");

        // Получение списка всех пользователей после удаления
        log.info("Получение списка всех пользователей");
        System.out.println(userService.getAllUsers());
        log.info("Пользователи успешно получены");

        // Обновление пользователя
        log.info("Обновление пользователя");
        user1.setName("Дмитрий");
        log.info("Отправление запроса на обновления пользователя {}", createdUser1);
        UserDto updatedUser = userService.updateUser(createdUser1.getId(), user1);
        log.info("Пользователь успешно обновлен {}", updatedUser);
        System.out.println(updatedUser);
    }
}