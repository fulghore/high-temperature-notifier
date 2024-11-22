package sumob.gecot.temperature_notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sumob.gecot.temperature_notifier.domain.model.Name;
import sumob.gecot.temperature_notifier.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendEmail(String message) {
        List<Name> users = userRepository.findAll();

        if (users.isEmpty()) {
            System.out.println("Nenhum usuário registrado para envio de e-mails.");
            return;
        }

        String[] emails = users.stream()
                .map(user -> user.getEmail() != null ? user.getEmail().getEmail() : null) // Certifique-se de que está retornando um String
                .filter(email -> email != null && !email.isEmpty())
                .toArray(String[]::new);

        System.out.println("E-mails encontrados: " + Arrays.toString(emails));

        if (emails.length == 0) {
            System.out.println("Nenhum e-mail válido encontrado.");
            return;
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emails);
        email.setSubject("Alerta de Temperatura Elevada");
        email.setText(message);

        try {
            emailSender.send(email);
            System.out.println("E-mail enviado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
