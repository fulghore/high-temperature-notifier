package sumob.gecot.temperature_notifier.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sumob.gecot.temperature_notifier.dto.WeatherResponse;

@Service
public class ServicePrevision {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ServicePrevision(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getTemperature() {
        String apiUrl = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=Belo%%20Horizonte&appid=%s&units=metric",
                apiKey
        );

        WeatherResponse response = restTemplate.getForObject(apiUrl, WeatherResponse.class);
        if (response != null && response.getMain() != null) {
            return response.getMain().getTemp();
        }
        throw new IllegalStateException("Failed to retrieve temperature data.");
    }
}
