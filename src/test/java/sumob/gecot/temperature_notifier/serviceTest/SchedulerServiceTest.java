package sumob.gecot.temperature_notifier.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sumob.gecot.temperature_notifier.service.EmailService;
import sumob.gecot.temperature_notifier.service.SchedulerService;
import sumob.gecot.temperature_notifier.service.ServicePrevision;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerServiceTest {

    @Mock
    private ServicePrevision servicePrevision;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        // Configuração inicial antes de cada teste, se necessário
    }

    @Test
    void testCheckTemperature_SendsEmail_WhenTemperatureIsAboveThreshold() {
        // Arrange
        double temperature = 35.0; // Temperatura acima do limite
        when(servicePrevision.getTemperature()).thenReturn(temperature);
        System.out.println("Temperatura atual: " + temperature);

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, times(1)).sendEmail("Temperatura atual acima do limite: " + temperature);
    }

    @Test
    void testCheckTemperature_DoesNotSendEmail_WhenTemperatureIsBelowThreshold() {
        // Arrange
        double temperature = 5.0; // Temperatura abaixo do limite
        when(servicePrevision.getTemperature()).thenReturn(temperature);

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, never()).sendEmail(anyString());
    }

    @Test
    void testCheckTemperature_HandlesException() {
        // Arrange
        when(servicePrevision.getTemperature()).thenThrow(new RuntimeException("Erro ao obter temperatura"));

        // Act
        schedulerService.checkTemperature();

        // Assert
        // Verifica se o método sendEmail não foi chamado
        verify(emailService, never()).sendEmail(anyString());
    }

}
