package sumob.gecot.temperature_notifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sumob.gecot.temperature_notifier.dto.TemperatureInfo;

import java.time.LocalTime;
import java.util.List;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private ServicePrevision servicePrevision;

    @Autowired
    private EmailService emailService;

    // Definindo o limite de temperatura como uma constante
    @Value("${temperature.threshold:20.0}")
    private double temperatureThreshold;

    @Scheduled(fixedRate = 60000) // A cada 1 minuto
    public void checkTemperature() {
        try {
            List<TemperatureInfo> currentTemperatures = servicePrevision.getTemperatures(); // Chama o método correto
            logger.info("Temperaturas atuais: {}", currentTemperatures);

            // Determina a saudação com base na hora atual
            String greeting = getGreeting();
            StringBuilder messageBuilder = new StringBuilder(greeting + "\nPrezados supervisores,\n Segue abaixo alerta sobre as temperaturas atuais registradas nas localidades monitoradas. \n\n");
            messageBuilder.append("Temperaturas atuais:\n\n");

            for (TemperatureInfo temperatureInfo : currentTemperatures) {
                messageBuilder.append(temperatureInfo.getLocationName())
                        .append(": ").append(temperatureInfo.getTemperature()).append("°C\n");
            }

            // Verifica se alguma temperatura está acima do limite
            boolean isAboveThreshold = currentTemperatures.stream()
                    .anyMatch(tempInfo -> tempInfo.getTemperature() > temperatureThreshold);

            if (isAboveThreshold) {
                messageBuilder.append("\nAtenção: Uma ou mais temperaturas estão acima do limite de ")
                        .append(temperatureThreshold).append("°C.");
            }

            // Envia um único e-mail com todas as temperaturas
            emailService.sendEmail(messageBuilder.toString());
            logger.info("E-mail enviado com as temperaturas atuais.");

        } catch (Exception e) {
            logger.error("Erro ao verificar a temperatura: {}", e.getMessage());
        }
    }

    private String getGreeting() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.NOON)) {
            return "Bom dia.\n";
        } else {
            return "Boa tarde.\n";
        }
    }
}