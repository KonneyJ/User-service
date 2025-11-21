package org.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий событие, связанное с изменением состояния пользовательского аккаунта.
 *
 * <p>Содержит информацию о типе операции (создание/удаление аккаунта) и email пользователя,
 * для которого произошло событие. Используется в механизмах асинхронной коммуникации
 * между микросервисами через Kafka для передачи данных о событиях жизненного
 * цикла аккаунта.</p>
 *
 * @see Operation — перечисление типов операций над аккаунтом.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    /**
     * Тип операции, выполненной над пользовательским аккаунтом.
     *
     * Определяет характер события: создание или удаление аккаунта.
     */
    private Operation operation;

    /**
     * Email пользователя, с которым связано событие.
     */
    private String email;
}
