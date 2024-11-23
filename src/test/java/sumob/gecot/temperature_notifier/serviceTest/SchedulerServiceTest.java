package sumob.gecot.temperature_notifier.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sumob.gecot.temperature_notifier.dto.TemperatureInfo;
import sumob.gecot.temperature_notifier.service.EmailService;
import sumob.gecot.temperature_notifier.service.SchedulerService;
import sumob.gecot.temperature_notifier.service.ServicePrevision;

import java.util.Arrays;
import java.util.List;

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
        List<TemperatureInfo> temperatures = Arrays.asList(
                new TemperatureInfo("Est. Pampulha", 35.0) // Temperatura acima do limite
        );
        when(servicePrevision.getTemperatures()).thenReturn(temperatures);

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, times(1)).sendEmail(anyString()); // Verifica se o e-mail foi enviado
    }

    @Test
    void testCheckTemperature_DoesNotSendEmail_WhenTemperatureIsBelowThreshold() {
        // Arrange
        List<TemperatureInfo> temperatures = Arrays.asList(
                new TemperatureInfo("Est. Pampulha", 18.0) // Temperatura abaixo do limite
        );
        when(servicePrevision.getTemperatures()).thenReturn(temperatures);

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, never()).sendEmail(anyString()); // Verifica se o e-mail não foi enviado
    }

    @Test
    void testCheckTemperature_SendsEmail_WhenSomeTemperaturesAreAboveThreshold() {
        // Arrange
        List<TemperatureInfo> temperatures = Arrays.asList(
                new TemperatureInfo("Est. Pampulha", 18.0), // Abaixo do limite
                new TemperatureInfo("Est. Venda Nova", 25.0) // Acima do limite
        );
        when(servicePrevision.getTemperatures()).thenReturn(temperatures);

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, times(1)).sendEmail(anyString()); // Verifica se o e-mail foi enviado
    }

    @Test
    void testCheckTemperature_SendsEmail_WhenMultipleTemperaturesAreAboveThreshold() {
        // Arrange
        List<TemperatureInfo> temperatures = Arrays.asList(
                new TemperatureInfo("Est. Pampulha", 22.0),
                new TemperatureInfo("Est. Venda Nova", 25.0),
                new TemperatureInfo("Est. Centro", 19.0) // Abaixo do limite
        );
        when(servicePrevision.getTemperatures()).thenReturn(temperatures);

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, times(1)).sendEmail(anyString()); // Verifica se o e-mail foi enviado
    }

    @Test
    void testCheckTemperature_HandlesException() {
        // Arrange
        when(servicePrevision.getTemperatures()).thenThrow(new RuntimeException("Erro ao obter temperatura"));

        // Act
        schedulerService.checkTemperature();

        // Assert
        verify(emailService, never()).sendEmail(anyString()); // Verifica se o e-mail não foi enviado
    }
}