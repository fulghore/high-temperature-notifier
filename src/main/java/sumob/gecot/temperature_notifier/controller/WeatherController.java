package sumob.gecot.temperature_notifier.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Weather Controller", description = "API para obter informações sobre a temperatura atual")
public class WeatherController {

    @Operation(summary = "Obter temperatura", description = "Retorna a temperatura atual de Belo Horizonte.")
    @GetMapping("/temperature")
    public String getTemperature() {
        return "Temperatura atual: 25ºC";
    }

}
