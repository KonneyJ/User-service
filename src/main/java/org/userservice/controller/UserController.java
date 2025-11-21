package org.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Операции с пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать пользователя", description = "Создание нового пользователя в системе")
    @ApiResponse(responseCode = "201", description = "Пользователь создан",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public EntityModel<UserDto> createUser(
            @Parameter(description = "Данные пользователя для создания", required = true)
            @RequestBody UserDto user) {
        log.info("POST /users запрос with UserDto {}", user);

        UserDto userCreated = userService.createUser(user);

        EntityModel<UserDto> resource = EntityModel.of(userCreated);

        resource.add(linkTo(methodOn(UserController.class).getUserById(userCreated.getId())).withSelfRel());

        return resource;
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновление пользователя",
            description = "Обновляет указанные поля пользователя по ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно обновлён",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные в теле запроса"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь с указанным ID не найден"
            )
    })
    public EntityModel<UserDto> updateUser(@Parameter(description = "ID пользователя, которого нужно обновить", required = true)
                                           @PathVariable int id,
                                           @Parameter(description = "Поля пользователя для обновления", required = true)
                                           @RequestBody UserDto user) {
        log.info("PATCH /users/id запрос with id {}, UserDto {}", id, user);

        UserDto userUpdated = userService.updateUser(id, user);

        EntityModel<UserDto> resource = EntityModel.of(userUpdated);

        resource.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("collection"));

        return resource;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление пользователя",
            description = "Удаляет пользователя по указанному ID. Возвращает true при успешном удалении."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь удалён успешно",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь с указанным ID не найден"
            )
    })
    public EntityModel<Boolean> deleteUser(@Parameter(description = "ID пользователя для удаления", required = true)
                                           @PathVariable int id) {
        log.info("DELETE /users/id запрос with id {}", id);

        boolean wasDeleted = userService.deleteUser(id);

        EntityModel<Boolean> resource = EntityModel.of(wasDeleted);

        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("collection"));

        return resource;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение пользователя по ID",
            description = "Возвращает полную информацию о пользователе с указанным ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь найден и возвращён",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь с указанным ID не найден"
            )
    })
    public EntityModel<UserDto> getUserById(@Parameter(description = "ID запрашиваемого пользователя", required = true)
                                            @PathVariable int id) {
        log.info("GET /users запрос with id {}", id);

        UserDto user = userService.getUserById(id);

        EntityModel<UserDto> resource = EntityModel.of(user);

        resource.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("collection"));

        return resource;
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех пользователей",
            description = "Возвращает коллекцию всех пользователей в системе. Поддерживает пагинацию (если реализовано)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список пользователей успешно возвращён",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                    )
            )
    })
    public CollectionModel<EntityModel<UserDto>> getAllUsers() {
        log.info("GET /users запрос");

        Collection<UserDto> users = userService.getAllUsers();

        List<EntityModel<UserDto>> resources = users.stream()
                .map(user -> {
                    EntityModel<UserDto> resource = EntityModel.of(user);
                    resource.add(linkTo(methodOn(UserController.class)
                            .getUserById(user.getId())).withSelfRel());
                    return resource;
                })
                .collect(Collectors.toList());

        return CollectionModel.of(resources,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }
}
