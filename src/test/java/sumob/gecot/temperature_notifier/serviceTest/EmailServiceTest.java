package sumob.gecot.temperature_notifier.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import sumob.gecot.temperature_notifier.model.User;
import sumob.gecot.temperature_notifier.repository.UserRepository;
import sumob.gecot.temperature_notifier.service.EmailService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        // Configuração inicial antes de cada teste, se necessário
    }

    @Test
    void testSendEmail() {
        // Mock do repositório retornando usuários com e-mails válidos
        User user1 = new User("luffy@op.com");
        User user2 = new User("zoro@op.com");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Mock do envio de e-mail
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        // Execução do método
        emailService.sendEmail("Temperatura alta!");

        // Validação do número de chamadas
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
