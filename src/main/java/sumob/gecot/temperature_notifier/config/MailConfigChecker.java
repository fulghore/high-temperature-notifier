package sumob.gecot.temperature_notifier.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfigChecker {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @PostConstruct
    public void checkMailConfig() {
        System.out.println("Mail Host: " + mailHost);
        System.out.println("Mail Port: " + mailPort);
        System.out.println("Mail Username: " + mailUsername);
        System.out.println("Mail Password: " + (mailPassword != null ? "****" : "NOT SET"));
    }
}
