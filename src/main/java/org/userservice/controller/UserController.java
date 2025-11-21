package org.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.userservice.dto.UserDto;
import org.userservice.service.UserService;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto user) {
        log.info("POST /users запрос with UserDto {}", user);

        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable int id, @RequestBody UserDto user) {
        log.info("PATCH /users/id запрос with id {}, UserDto {}", id, user);

        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        log.info("DELETE /users/id запрос with id {}", id);

        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        log.info("GET /users запрос with id {}", id);

        return userService.getUserById(id);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("GET /users запрос");

        return userService.getAllUsers();
    }
}
