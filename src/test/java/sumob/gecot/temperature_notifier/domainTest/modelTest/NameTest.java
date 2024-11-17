package sumob.gecot.temperature_notifier.domainTest.modelTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sumob.gecot.temperature_notifier.domain.model.Name;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNameCreationWithValidLength() {
        Name name = new Name();
        name.setName("Valid Name");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameCreationWithMaxLength() {
        Name name = new Name();
        name.setName("a".repeat(50)); // 50 caracteres
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameCreationWithExceedingLength() {
        Name name = new Name();
        name.setName("a".repeat(51)); // 51 caracteres
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertEquals(1, violations.size());
        assertEquals("tamanho deve ser entre 0 e 50", violations.iterator().next().getMessage());
    }

    @Test
    void testNameCreationWithNullName() {
        Name name = new Name();
        name.setName(null);
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertEquals(1, violations.size());
        assertEquals("não deve ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testNameCreationWithValidEmail() {
        Name name = new Name("test@example.com");
        assertNotNull(name.getEmail());
        assertEquals("test@example.com", name.getEmail().getEmail());
    }

    @Test
    void testNameCreationWithInvalidEmail() {
        Name name = new Name("invalid-email");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertEquals(1, violations.size());
        assertEquals("não deve ser nulo", violations.iterator().next().getMessage());
    }
}
