package org.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) для передачи данных о пользователе между слоями приложения.
 *
 * <p>
 * Класс инкапсулирует основные атрибуты пользователя:
 * - идентификатор;
 * - имя;
 * - email;
 * - возраст.
 * </p>
 *
 * <p>
 * Используется для:
 * - передачи данных из сервиса в контроллер (и обратно);
 * - сериализации/десериализации (например, при обмене JSON через REST API);
 * - изоляции модели данных от внешних клиентов.
 * </p>
 *
 * <p>
 * <strong>Особенности реализации:</strong>
 * - Класс использует Lombok для генерации геттеров, сеттеров, конструкторов и билдера.
 * - Поля не имеют валидации на уровне класса (валидация должна выполняться на уровне сервиса или контроллера).
 * </p>
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @see lombok.Builder
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserDto {
    /**
     * Уникальный идентификатор пользователя в системе.
     */
    private int id;
    /**
     * Полное имя пользователя.
     */
    private String name;
    /**
     * Адрес электронной почты пользователя.
     */
    private String email;
    /**
     * Возраст пользователя в годах.
     */
    private short age;
}
