import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.userservice.controller.UserController;
import org.userservice.service.kafka.EmailServiceImlp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import(EmailServiceImlp.class)
public class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaMailSender mailSender;
    private SimpleMailMessage message;

    @BeforeEach
    void setUp() {
        message = new SimpleMailMessage();
        message.setTo("test@example.com");
        message.setSubject("Тест");
        message.setText("Проверка теста");
    }

    @SneakyThrows
    @Test
    void shouldSendCreatedEmail() {
        mockMvc.perform(post("/notifications/created")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Email отправлен на test@example.com"));

        verify(mailSender, times(1)).send(message);
    }

    @SneakyThrows
    @Test
    void shouldSendDeletedEmail() {
        mockMvc.perform(post("/notifications/deleted")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Email отправлен на test@example.com"));

        verify(mailSender, times(1)).send(message);
    }
}
