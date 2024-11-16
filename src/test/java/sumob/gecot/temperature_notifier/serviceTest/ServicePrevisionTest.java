
package sumob.gecot.temperature_notifier.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import sumob.gecot.temperature_notifier.dto.WeatherResponse;
import sumob.gecot.temperature_notifier.service.ServicePrevision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicePrevisionTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ServicePrevision servicePrevision;

    @Test
    void testGetTemperature_ReturnsCorrectTemperature() {
        // Arrange
        double expectedTemperature = 20.0;
        WeatherResponse mockResponse = new WeatherResponse();
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(expectedTemperature);
        mockResponse.setMain(main);

        // Simula o comportamento do RestTemplate
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(WeatherResponse.class)))
                .thenReturn(mockResponse);

        // Act
        double actualTemperature = servicePrevision.getTemperature();

        // Assert
        assertEquals(expectedTemperature, actualTemperature);

    }

    @Test
    void testGetTemperature_ThrowsException_WhenResponseIsNull() {
        // Arrange
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(WeatherResponse.class)))
                .thenReturn(null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> servicePrevision.getTemperature());
    }

    @Test
    void testGetTemperature_ThrowsException_WhenMainIsNull() {
        // Arrange
        WeatherResponse mockResponse = new WeatherResponse();
        mockResponse.setMain(null);

        // Simula o comportamento do RestTemplate
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(WeatherResponse.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> servicePrevision.getTemperature());
    }
}