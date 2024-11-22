package sumob.gecot.temperature_notifier.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sumob.gecot.temperature_notifier.dto.TemperatureInfo;
import sumob.gecot.temperature_notifier.dto.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicePrevision {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // Nomes dos locais correspondentes às coordenadas
    private final String[] locationNames = {
            "Est. Pampulha",
            "Est. Venda Nova",
            "Est. Vilarinho",
            "Est. São Gabriel",
            "Est. Barreiro",
            "Est. Diamante",
            "Praça Sete"
    };

    public ServicePrevision(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TemperatureInfo> getTemperatures() {
        List<TemperatureInfo> temperatureInfos = new ArrayList<>();

        // Lista de coordenadas (latitude e longitude) para os locais específicos
        double[][] locations = {
                {-19.842785978148825, -43.96688534831738}, // Est. Pampulha
                {-19.80940485928533, -43.96933415055714}, // Est. Venda Nova
                {-19.82156023618599, -43.94735203432492}, // Est. Vilarinho
                {-19.864085423190797, -43.927551923029746}, // Est. São Gabriel
                {-19.97389078183402, -44.0210480945352}, // Est. Barreiro
                {-19.99453303160576, -44.02409118734038},    // Est. Diamante
                {-19.919133544952057, -43.938613776717695}    // Praça Sete
        };

        String baseUrl = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

        for (int i = 0; i < locations.length; i++) {
            double[] location = locations[i];
            String apiUrl = String.format(baseUrl, location[0], location[1], apiKey);

            WeatherResponse response = restTemplate.getForObject(apiUrl, WeatherResponse.class);
            if (response != null && response.getMain() != null) {
                double temperature = response.getMain().getTemp();
                temperatureInfos.add(new TemperatureInfo(locationNames[i], temperature));
            } else {
                throw new IllegalStateException("Failed to retrieve temperature data for location: " + location[0] + ", " + location[1]);
            }
        }

        return temperatureInfos;
    }
}