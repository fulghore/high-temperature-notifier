package sumob.gecot.temperature_notifier.domainTest.modelTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sumob.gecot.temperature_notifier.domain.model.Email;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEmailCreationWithValidEmail() {
        Email email = new Email("test@example.com");
        assertNotNull(email);
        assertEquals("test@example.com", email.getEmail());
    }

    @Test
    void testEmailCreationWithNullEmail() {
        Email email = new Email(null);
        Set<ConstraintViolation<Email>> violations = validator.validate(email);
        assertEquals(1, violations.size());
        assertEquals("não deve ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailCreationWithInvalidEmail() {
        Email email = new Email("invalid-email");
        Set<ConstraintViolation<Email>> violations = validator.validate(email);
        assertEquals(1, violations.size());
        assertEquals("deve ser um endereço de e-mail bem formado", violations.iterator().next().getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");
        Email email3 = new Email("test2@example.com");

        email1.setId(1L);
        email2.setId(1L);
        email3.setId(2L);

        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotEquals(email1.hashCode(), email3.hashCode());
    }

    @Test
    void testToString() {
        Email email = new Email("test@example.com");
        email.setId(1L);
        String expectedString = "Email{id=1, email='test@example.com'}";
        assertEquals(expectedString, email.toString());
    }

    // Teste adicional para verificar a formatação do corpo do e-mail
    @Test
    void testEmailBodyFormatting() {
        String body = "Bom dia,\n" +
                "Prezados supervisores,\n\n" +
                "Temperaturas atuais:\n" +
                "- Localidade 1: 25.0°C\n" +
                "- Localidade 2: 22.5°C\n" +
                "\nAtenção: Uma ou mais temperaturas estão acima do limite de 20.0°C. As localizações afetadas são:\n" +
                "- Localidade 1: 25.0°C\n" +
                "- Localidade 2: 22.5°C\n" +
                "\nRecomendamos que sejam tomadas as devidas providências para monitorar essas condições.";

        assertNotNull(body);
        assertTrue(body.contains("Bom dia,"));
        assertTrue(body.contains("Prezados supervisores,"));
        assertTrue(body.contains("Temperaturas atuais:"));
        assertTrue(body.contains("Atenção: Uma ou mais temperaturas estão acima do limite de 20.0°C"));
    }
}