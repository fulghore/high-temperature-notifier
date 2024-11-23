package sumob.gecot.temperature_notifier.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import sumob.gecot.temperature_notifier.dto.TemperatureInfo;
import sumob.gecot.temperature_notifier.dto.WeatherResponse;
import sumob.gecot.temperature_notifier.service.ServicePrevision;

import java.util.List;

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
    void testGetTemperature_ReturnsCorrectTemperatures() {
        // Arrange
        WeatherResponse[] mockResponses = new WeatherResponse[7];
        for (int i = 0; i < 7; i++) {
            mockResponses[i] = new WeatherResponse();
            WeatherResponse.Main main = new WeatherResponse.Main();
            main.setTemp(20.0 + i); // Define temperaturas de 20.0 a 26.0
            mockResponses[i].setMain(main);
        }

        // Simula o comportamento do RestTemplate
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(WeatherResponse.class)))
                .thenReturn(mockResponses[0], mockResponses[1], mockResponses[2],
                        mockResponses[3], mockResponses[4], mockResponses[5],
                        mockResponses[6]); // Simula múltiplas chamadas

        // Act
        List<TemperatureInfo> actualTemperatures = servicePrevision.getTemperatures();

        // Assert
        assertEquals(7, actualTemperatures.size()); // Verifica se há 7 itens na lista
        for (int i = 0; i < 7; i++) {
            assertEquals(20.0 + i, actualTemperatures.get(i).getTemperature());
                    }
    }

    @Test
    void testGetTemperature_ThrowsException_WhenResponseIsNull() {
        // Arrange
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(WeatherResponse.class)))
                .thenReturn(null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> servicePrevision.getTemperatures());
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
        assertThrows(IllegalStateException.class, () -> servicePrevision.getTemperatures());
    }
}