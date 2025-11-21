package org.userservice.service.kafka;

/**
 * Сервис для отправки email‑уведомлений о ключевых событиях жизненного цикла пользовательского аккаунта.
 */
public interface EmailService {
    /**
     * Отправляет email‑уведомление о создании пользовательского аккаунта.
     */
    void sendCreatedEmail(String to);

    /**
     * Отправляет email‑уведомление об удалении пользовательского аккаунта.
     */
    void sendDeletedEmail(String to);
}
